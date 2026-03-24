package de.thd.systemdesign.p2p.messages;

import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.ArrayList;
import java.util.List;



@JsonTypeName("pongmessage")
public class PongMessage extends P2PMessage {

    private List<String> nodes = new ArrayList<>();
    private PongMessage() {}

    public PongMessage(String nodeid,  List<String> nodes) {
        this.source = nodeid;
        this.nodes = nodes;
    }
    public List<String> getNodes() { return nodes; }
    public void setNodes(List<String> nodes) { this.nodes = nodes; }

    @Override
    public String getEndpoint() {
        return "pong";
    }

}