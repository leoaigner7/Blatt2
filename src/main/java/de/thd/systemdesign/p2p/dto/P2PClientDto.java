package de.thd.systemdesign.p2p.dto;

import java.time.LocalDateTime;

public class P2PClientDto {
    String nodeid;
    LocalDateTime lastSeen;

    public P2PClientDto(String nodeid, LocalDateTime lastSeen) {
        this.nodeid = nodeid;
        this.lastSeen = lastSeen;
    }

    public String getNodeid() {
        return nodeid;
    }

    public void setNodeid(String nodeid) {
        this.nodeid = nodeid;
    }

    public LocalDateTime getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(LocalDateTime lastSeen) {
        this.lastSeen = lastSeen;
    }
}