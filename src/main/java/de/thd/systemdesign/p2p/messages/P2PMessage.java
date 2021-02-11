package de.thd.systemdesign.p2p.messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * The base class of all messages.
 *
 * Inheritance in JSON is not easy, therefore we use the @Json... Annotations to identify classes using the type property added by Jackson.
 *
 * https://www.baeldung.com/jackson-annotations
 */

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SearchResponseMessage.class, name = "searchresponse"),
        @JsonSubTypes.Type(value = SearchMessage.class, name = "searchrequest"),
        @JsonSubTypes.Type(value = PingMessage.class, name = "pingmessage"),
        @JsonSubTypes.Type(value = RegistrationMessage.class, name = "registrationmessage"),
        @JsonSubTypes.Type(value = PongMessage.class, name = "pongmessage")
}) // Maybe we do not need this, cause it is also contained in the subclasses?
public abstract class P2PMessage {
    String source;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    /**
     * Determines the http endpoint, that expects this kind of message.
     * Needs to be implemented by all message sub-classes
     */
    @JsonIgnore
    public abstract String getEndpoint();
}