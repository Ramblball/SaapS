package com.example.SappS.database.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user")
public class User {
    @Id
    private String id;

    @NonNull
    @Indexed(unique = true)
    private String name;
    @NonNull
    private Integer age;
    @NonNull
    @JsonIgnore
    private String password;
    @NonNull
    private String city;
}
