package com.example.SappS.database.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    String id;
    @NonNull
    @Indexed(unique = true)
    String name;
    @NonNull
    Integer age;
    @NonNull
    String password;
    @NonNull
    String city;
}
