package de.thd.systemdesign.p2p.service;

import de.thd.systemdesign.p2p.config.NodeConfig;
import de.thd.systemdesign.p2p.dto.P2PClientDto;
import de.thd.systemdesign.p2p.messages.PingMessage;
import de.thd.systemdesign.p2p.model.P2PClient;
import de.thd.systemdesign.p2p.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private static Logger log = LoggerFactory.getLogger(ClientService.class);

    @Autowired
    private Environment env;

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private NodeConfig cfg;

    @Autowired
    private ClientRepository clientRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void bootClient() {
        int port = Integer.parseInt(System.getProperty("server.port", "8080"));
        boolean isMaster = System.getenv("ISMASTER") != null;

        // This is only used for tests
        if (env.getProperty("p2p.ismaster.itsme") != null)
            isMaster = true;

        cfg.setPort(port);
        cfg.setMaster(isMaster);
        if (isMaster)
            cfg.setNode(env.getProperty("p2p.master.url"));

        if (isMaster) {
            log.info("=========================   MASTER   =========================");
        } else {
            log.info("I AM NOT MASTER");
            connectionService.registerAtMaster(port);
            this.addMasterNode();
        }
    }

    public Optional<P2PClientDto> findByIdDto(String nodeid) {
        return clientRepository.findByIdDto(nodeid);
    }

    public List<P2PClient> findAll() {
        return clientRepository.findAll();
    }

    public List<P2PClientDto> findAllDto() {
        return clientRepository.findAllDto();
    }

    public P2PClient register(P2PClient client) {
        return addOrTouch(client);
    }

    // Transactional, otherwise it will not auto save the changes here
    @Transactional
    public P2PClient addOrTouch(P2PClient client) {
        // TODO Check if we try to add ourselves
        P2PClient ret = clientRepository.save(client);
        return ret;
    }

    public void gotPing(String name) {
        P2PClient client = new P2PClient(name);
        addOrTouch(client);
    }

    public void doPing(String nodeid) {
        PingMessage pingMessage = new PingMessage(cfg.getNode());
        connectionService.sendTo(nodeid, pingMessage);
    }

    // Not Transactional
    public void cleanupOrphanNodes() {
        Duration d = Duration.ofMinutes(5);
        LocalDateTime thresh = LocalDateTime.now().minus(d);

        // Remove Everything
        clientRepository.deleteAllByLastModificationDate(thresh);
        // In the master we trust
        if (!cfg.isMaster())
            this.addMasterNode();
    }

    // Not Transactional
    private void addMasterNode() {
        P2PClient master = new P2PClient(env.getProperty("p2p.master.url"));
        this.addOrTouch(master);
    }
}