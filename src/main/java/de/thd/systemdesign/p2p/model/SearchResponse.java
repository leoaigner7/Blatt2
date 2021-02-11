package de.thd.systemdesign.p2p.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tbl_searchresponse")
public class SearchResponse {
    @Id
    String uid;
    String response;
    LocalDateTime received;

    public SearchResponse() {}

    public SearchResponse(String uid, String response) {
        this.uid = uid;
        this.response = response;
    }

    @PrePersist
    public void preStore() {
        this.received = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SearchResponse)) return false;
        SearchResponse search = (SearchResponse) o;
        return uid.equals(search.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid);
    }

    public String getUid() {
        return uid;
    }

    public String getResponse() {
        return response;
    }

    public LocalDateTime getCreated() {
        return received;
    }
}