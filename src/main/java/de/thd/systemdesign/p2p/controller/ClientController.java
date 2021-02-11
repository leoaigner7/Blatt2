package de.thd.systemdesign.p2p.controller;

import de.thd.systemdesign.p2p.dto.P2PClientDto;
import de.thd.systemdesign.p2p.messages.PingMessage;
import de.thd.systemdesign.p2p.messages.PongMessage;
import de.thd.systemdesign.p2p.messages.RegistrationMessage;
import de.thd.systemdesign.p2p.model.P2PClient;
import de.thd.systemdesign.p2p.service.ClientService;
import de.thd.systemdesign.p2p.utility.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ClientController {
    private static Logger log = LoggerFactory.getLogger(ClientController.class);

    private ClientService clientService;

    ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/clients/")
    List<P2PClientDto> list() {
        return clientService.findAllDto();
    }

    @PostMapping("/register/")
    String register(HttpServletRequest request, @RequestBody RegistrationMessage reg) {
        log.info("Remote Registration: " + request.getRemoteAddr() + " port " + reg.getPort());
        String ip = Utils.getNodeName(request);
        P2PClient client = new P2PClient(String.format("%s:%s", ip, reg.getPort()));
        client = clientService.register(client);
        return client.getNodeid();
    }

    @PostMapping("/ping/")
    PongMessage ping(HttpServletRequest request, @RequestBody PingMessage msg) {
        log.info("Ping from : " + msg.getSource());
        String name = Utils.getNodeName(request);
        clientService.gotPing(msg.getSource());
        return new PongMessage(name);
    }

}