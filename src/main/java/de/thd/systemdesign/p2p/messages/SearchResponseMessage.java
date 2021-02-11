package de.thd.systemdesign.p2p.messages;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("searchresponse")
public class SearchResponseMessage extends P2PMessage {
    // Currently no ide
    String location;
    // That is a reply to the search with that ud
    String uid;

    private SearchResponseMessage() {}

    public SearchResponseMessage(String source, String uid, String location) {
        this.source = source;
        this.uid = uid;
        this.location = location;
    }

    @Override
    public String getEndpoint() {
        return String.format("%s/%s", "search", uid);
    }

    public String getLocation() {
        return location;
    }

    public String getUid() {
        return uid;
    }
}