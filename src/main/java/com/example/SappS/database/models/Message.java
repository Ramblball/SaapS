package com.example.SappS.database.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "message")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Message {

    @Id
    String id;
    String chatId;
    String senderId;
    String receiverId;
    String senderName;
    String receiverName;
    String message;
}
