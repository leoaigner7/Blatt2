package de.thd.systemdesign.p2p.messages;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonTypeName("forward")
public class ForwardedMessage extends P2PMessage {
    List<String> hops;
    P2PMessage msg;

    public ForwardedMessage() {
        this.hops = new ArrayList<>();
        this.source = "";
    }

    public ForwardedMessage(String source) {
        this();
        this.source = source;
        this.hops.add(source);
    }

    public boolean visited(String id) {
        Set<String> myhops = new HashSet<>(hops);
        return myhops.contains(id);
    }

    public List<String> getHops() {
        return hops;
    }

    public void setHops(List<String> hops) {
        this.hops = hops;
    }

    public P2PMessage getMsg() {
        return msg;
    }

    public void setMsg(P2PMessage msg) {
        this.msg = msg;
    }

    @Override
    public String getEndpoint() {
        return msg.getEndpoint();
    }

    public void addHop(String node) {
        // This should be checked on entry. If forgotten, crash
        assert(visited(node) == false);
        this.hops.add(node);
    }
}