package com.example.SappS.database.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "room")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Room {

    @Id
    String id;
    @NonNull
    String chatId;
    @NonNull
    String senderId;
    @NonNull
    String receiverId;
}
