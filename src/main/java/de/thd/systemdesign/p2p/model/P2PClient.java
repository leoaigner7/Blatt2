package de.thd.systemdesign.p2p.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_client")
public class P2PClient {
    @Id
    private String nodeid;
    private LocalDateTime created;
    private LocalDateTime lastSeen;

    public P2PClient() {
    }

    public P2PClient(String nodeid) {
        this.nodeid = nodeid;
    }

    @PrePersist
    public void preStore() {
        this.created = LocalDateTime.now();
        this.lastSeen = this.created;
    }

    @PreUpdate
    public void preUpdate() {
        this.lastSeen = LocalDateTime.now();
    }

    public String getNodeid() {
        return nodeid;
    }

    public void setNodeid(String nodeid) {
        this.nodeid = nodeid;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getLastSeen() {
        return lastSeen;
    }

    public void touch() {
        this.preUpdate();
    }
}