package de.thd.systemdesign.p2p.scheduler;

import de.thd.systemdesign.p2p.dto.P2PClientDto;
import de.thd.systemdesign.p2p.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PingTask {
    private static Logger log = LoggerFactory.getLogger(PingTask.class);

    ClientService clientService;


    PingTask(ClientService clientService) {
        this.clientService = clientService;
    }

    @Scheduled(fixedRateString = "${timing.pingtask.fixedRate}", initialDelayString = "${timing.pingtask.initialDelay}")
    public void runPingTask() {
        log.info("Running PING Task");
        List<P2PClientDto> clients = clientService.findAllDto();
        log.info("Found " + clients.size() + " clients");
        Collections.shuffle(clients);

        // TODO: Could be moved to ConnectionService.sendMessage this way it would be central
        // TODO: Otherwise we need to limit this loop to a max number of nodes to contact
        for (P2PClientDto client : clients) {
            log.info("Ping " + client.getNodeid());
            try {
                clientService.doPing(client.getNodeid());
            } catch (Exception e) {
                log.info("Client " + client.getNodeid() + "has issues");
            }
        }
    }
}