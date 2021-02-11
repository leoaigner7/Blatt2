package de.thd.systemdesign.p2p.messages;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.UUID;

@JsonTypeName("searchrequest")
public class SearchMessage extends P2PMessage {
    String query;
    String uid;

    private SearchMessage() {}

    public SearchMessage(String sender, String query) {
        this.source = sender;
        this.query = query;
        this.uid = UUID.randomUUID().toString();
    }

    @Override
    public String getEndpoint() {
        return "search";
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getUid() { return this.uid; }
}