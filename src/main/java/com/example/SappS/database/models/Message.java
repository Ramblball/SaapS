package com.example.SappS.database.models;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "message")
public class Message {

    @Id
    private String id;
    @NonNull
    private String chatId;
    @NonNull
    private String senderId;
    @NonNull
    private String receiverId;
    @NonNull
    private String senderName;
    @NonNull
    private String receiverName;
    @NonNull
    private String message;
}
