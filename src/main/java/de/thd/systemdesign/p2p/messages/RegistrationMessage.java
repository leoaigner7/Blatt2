package de.thd.systemdesign.p2p.messages;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("registrationmessage")
public class RegistrationMessage extends P2PMessage {
    int port;

    private RegistrationMessage() { }

    public RegistrationMessage(int port) {
        this.port = port;
        this.source = "source";
    }

    @Override
    public String getEndpoint() {
        return "register";
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}