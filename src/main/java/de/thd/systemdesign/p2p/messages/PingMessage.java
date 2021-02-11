package de.thd.systemdesign.p2p.messages;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("pingmessage")
public class PingMessage extends P2PMessage {

    private PingMessage() { }

    public PingMessage(String source) {
        this.source = source;
    }

    @Override
    public String getEndpoint() {
        return "ping";
    }

}