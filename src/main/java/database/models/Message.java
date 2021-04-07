package database.models;

import database.Literals;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс - описание сущности сообщения
 */
public class Message implements Entity {
    private ObjectId id;
    private ObjectId sender;
    private ObjectId receiver;
    private String message;
    private Date timestamp;

    public Message(String sender, String receiver, String message) {
        this.sender = new ObjectId(sender);
        this.receiver = new ObjectId(receiver);
        this.message = message;
        this.timestamp = new Date();
    }

    public Message(Document document) {
        sender = document.getObjectId(Literals.FIELD_SENDER);
        receiver = document.getObjectId(Literals.FIELD_RECEIVER);
        message = document.getString(Literals.FIELD_MESSAGE);
        timestamp = document.getDate(Literals.FIELD_TIMESTAMP);
        if (document.containsKey(Literals.FIELD_ID)) {
            this.id = document.getObjectId(Literals.FIELD_ID);
        }
    }

    @Override
    public Document toDocument() {
        Map<String, Object> map = new HashMap<>();
        map.put(Literals.FIELD_SENDER, sender);
        map.put(Literals.FIELD_RECEIVER, receiver);
        map.put(Literals.FIELD_MESSAGE, message);
        if (id != null) {
            map.put(Literals.FIELD_ID, id);
        }
        return new Document(map);
    }

    @Override
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getSender() {
        return sender;
    }

    public void setSender(ObjectId sender) {
        this.sender = sender;
    }

    public ObjectId getReceiver() {
        return receiver;
    }

    public void setReceiver(ObjectId receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
