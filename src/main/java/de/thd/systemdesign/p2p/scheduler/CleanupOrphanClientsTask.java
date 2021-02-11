package de.thd.systemdesign.p2p.scheduler;

import de.thd.systemdesign.p2p.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CleanupOrphanClientsTask {
    private static Logger log = LoggerFactory.getLogger(CleanupOrphanClientsTask.class);

    ClientService clientService;

    CleanupOrphanClientsTask(ClientService clientService) {
        this.clientService = clientService;
    }

    @Scheduled(fixedRateString = "${timing.cleanuptask.fixedRate}", initialDelayString = "${timing.cleanuptask.initialDelay}")
    public void runCleanupTask() {
        log.info("Running Orphan nodes Removal Task");
        clientService.cleanupOrphanNodes();
    }
}