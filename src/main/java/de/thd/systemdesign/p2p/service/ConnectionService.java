package de.thd.systemdesign.p2p.service;

import de.thd.systemdesign.p2p.config.NodeConfig;
import de.thd.systemdesign.p2p.dto.P2PClientDto;
import de.thd.systemdesign.p2p.messages.ForwardedMessage;
import de.thd.systemdesign.p2p.messages.P2PMessage;
import de.thd.systemdesign.p2p.messages.RegistrationMessage;
import de.thd.systemdesign.p2p.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ConnectionService {
    private static Logger log = LoggerFactory.getLogger(ConnectionService.class);

    private Environment env;
    private NodeConfig cfg;
    private ClientRepository clientRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    public ConnectionService(NodeConfig cfg, ClientRepository clientRepository, Environment env) {
        this.cfg = cfg;
        this.clientRepository = clientRepository;
        this.env = env;
    }

    public void sendMessage(ForwardedMessage msg) {
        clientRepository.findAllDto().stream().limit(3).forEach(client -> {
            try {
                sendTo(client, msg);
            } catch (Exception e) {
                log.debug("Problem sending message to " + client.getNodeid());
            }
        });
    }

    // TODO: Merge sendMessage and forwardMessage
    // currently i like it because of the improved readability
    public void forwardMessage(ForwardedMessage msg) {
        log.info("Forwarding " + msg.getMsg().getClass().getSimpleName());

        clientRepository.findAllDto().stream()
            .filter(client -> msg.visited(client.getNodeid()) == false )
            .limit(3)
            .forEach(client -> {
                try {
                    sendTo(client, msg);
                } catch (Exception e) {
                    log.debug("Problem sending message to " + client.getNodeid());
                }
            });
    }

    /**
     * Send the message as part of a http post request to a client.
     * The response body is returned as a String.
     */
    public String sendTo(P2PClientDto client, P2PMessage msg) {
        return sendTo(client.getNodeid(), msg);
    }

    /**
     * Send the message as part of a http post request to a client.
     * The response body is returned as a String.
     */
    public String sendTo(String client, P2PMessage msg) {

        String url = String.format("http://%s/%s/", client, msg.getEndpoint());

        log.info("Sending message to " + url);

        HttpEntity<P2PMessage> entity = new HttpEntity<>(msg);
        ResponseEntity<String> response
                = restTemplate.postForEntity(url, entity, String.class);
        assert (response.getStatusCode().equals(HttpStatus.OK));
        return response.getBody();
    }

    public void registerAtMaster(int port) {
        log.info("REGISTER AT MASTER");

        RegistrationMessage reg = new RegistrationMessage(port);
        String myid = sendTo(env.getProperty("p2p.master.url"), reg);
        cfg.setNode(myid);

        log.info("I AM " + myid);
    }
}