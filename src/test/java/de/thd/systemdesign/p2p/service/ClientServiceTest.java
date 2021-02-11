package de.thd.systemdesign.p2p.service;

import de.thd.systemdesign.p2p.config.NodeConfig;
import de.thd.systemdesign.p2p.model.P2PClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(properties = { "p2p.master.itsme=1" })
class ClientServiceTest {

    @Autowired
    NodeConfig cfg;

    @Autowired
    ClientService clientService;

    @Test
    void findById() {
    }

    @Test
    void findByIdDto() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findAllDto() {
    }

    @Test
    void register() {
    }

    @Test
    void addOrTouch() throws InterruptedException {
        P2PClient node1a = new P2PClient("node1");
        P2PClient node1b = new P2PClient("node1");
        P2PClient node2a = new P2PClient("node2");
        P2PClient persist1a = clientService.addOrTouch(node1a);
        P2PClient persist1b = clientService.addOrTouch(node1b);
        assertEquals(persist1a.getNodeid(), persist1b.getNodeid());

        P2PClient persist2a = clientService.addOrTouch(node2a);
        assertNotEquals(persist1a.getNodeid(), persist2a.getNodeid());
        Thread.sleep(1000);

        P2PClient persist1c = clientService.addOrTouch(node1a);
        assertNotEquals(persist1a.getLastSeen(), persist1c.getLastSeen());
    }

    @Test
    void ping() {
    }

    @Sql("/sql/create-clients.sql")
    @Test
    void cleanupOrphanNodes() {
        this.cfg.setMaster(true);
        assertEquals(1, clientService.findAllDto().size());
        clientService.cleanupOrphanNodes();
        assertEquals(0, clientService.findAllDto().size());
    }
}