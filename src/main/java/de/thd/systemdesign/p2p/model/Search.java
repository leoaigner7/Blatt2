package de.thd.systemdesign.p2p.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tbl_search")
public class Search {
    @Id
    String uid;
    String query;
    LocalDateTime created;

    @PrePersist
    public void preStore() {
        this.created = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Search)) return false;
        Search search = (Search) o;
        return uid.equals(search.uid);
    }

    public Search() {}

    public Search(String uid, String query) {
        this.uid = uid;
        this.query = query;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid);
    }

    public String getUid() {
        return uid;
    }

    public String getQuery() {
        return query;
    }

    public LocalDateTime getCreated() {
        return created;
    }
}