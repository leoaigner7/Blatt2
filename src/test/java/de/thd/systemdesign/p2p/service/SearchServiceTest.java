package de.thd.systemdesign.p2p.service;

import de.thd.systemdesign.p2p.dto.P2PClientDto;
import de.thd.systemdesign.p2p.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.doReturn;

@SpringBootTest(properties = { "p2p.master.itsme=1" })
class SearchServiceTest {

    @Autowired
    Environment env;

    @Autowired
    SearchService searchService;

    @MockBean
    ClientRepository clientRepository;

    @Autowired
    ConnectionService connectionService;

    /*
    @MockBean
    ConnectionService connectionService;
    ClientRepository clientRepository;
*/
    List<P2PClientDto> nodes_dto = List.of(new P2PClientDto("node1", LocalDateTime.now()), new P2PClientDto("node2", LocalDateTime.now()), new P2PClientDto("node3", LocalDateTime.now()));

    @Test
    void testSend() {

        // Do not really try to send a message
  //      doAnswer(invocationOnMock -> { System.out.println("Send Message to " + invocationOnMock.getArgument(0)); return null; }).when(connectionService).sendTo(isA(String.class), isA(P2PMessage.class));
        doReturn(nodes_dto).when(clientRepository).findAllDto();
      //  doCallRealMethod().when(connectionService).sendMessage(any(ForwardedMessage.class));
        searchService.search_for("reason");

    }
}