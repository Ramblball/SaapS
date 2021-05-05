package com.example.SappS.database.models;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("service")
public class Service {
    @Id
    private String id;
    @NonNull
    private String name;
    @NonNull
    private List<Permission> permissions;
}
