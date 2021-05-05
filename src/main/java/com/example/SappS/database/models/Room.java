package com.example.SappS.database.models;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "room")
public class Room {
    @Id
    private String id;
    @NonNull
    private String firstId;
    @NonNull
    private String second;
}
