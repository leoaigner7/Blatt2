package de.thd.systemdesign.p2p;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.thd.systemdesign.p2p.messages.ForwardedMessage;
import de.thd.systemdesign.p2p.messages.PingMessage;
import de.thd.systemdesign.p2p.messages.SearchMessage;
import de.thd.systemdesign.p2p.messages.SearchResponseMessage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
@SpringBootTest
public class MessageTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testPingBoth() throws Exception {
        PingMessage p1 = new PingMessage("node1");
        String s = objectMapper.writeValueAsString(p1);
        System.out.println(s);
        PingMessage p2 = objectMapper.readValue(s, PingMessage.class);
        assertEquals(p1.getSource(), p2.getSource());
    }

    @Test
    public void testSearchResponeBoth() throws Exception {
        SearchResponseMessage sr1 = new SearchResponseMessage("node1:9090", UUID.randomUUID().toString(), "node1:9090/media/cat");
        String s = objectMapper.writeValueAsString(sr1);
        System.out.println(s);
        SearchResponseMessage sr2 = objectMapper.readValue(s, SearchResponseMessage.class);
        assertEquals(sr1.getSource(), sr2.getSource());
        assertEquals(sr1.getUid(), sr2.getUid());
        assertEquals(sr1.getLocation(), sr2.getLocation());
    }

    @Test
    public void testForwardSearchBoth() throws Exception {
        ForwardedMessage f1 = new ForwardedMessage("node2");
        SearchMessage s1 = new SearchMessage("node1", "cat");
        f1.setMsg(s1);

        String s = objectMapper.writeValueAsString(f1);
        System.out.println(s);
        ForwardedMessage f2 = objectMapper.readValue(s, ForwardedMessage.class);
        assertEquals(f1.getSource(), f2.getSource());
        assertEquals(s1.getSource(), f2.getMsg().getSource());
        assertEquals(s1.getQuery(), ((SearchMessage)f2.getMsg()).getQuery());
        assertEquals(s1.getUid(), ((SearchMessage)f2.getMsg()).getUid());
    }
}