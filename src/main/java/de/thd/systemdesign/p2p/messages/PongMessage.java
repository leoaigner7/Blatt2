package de.thd.systemdesign.p2p.messages;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("pongmessage")
public class PongMessage extends P2PMessage {

    private PongMessage() {}

    public PongMessage(String nodeid) {
        this.source = nodeid;
    }

    @Override
    public String getEndpoint() {
        return "pong";
    }

}