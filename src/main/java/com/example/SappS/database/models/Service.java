package com.example.SappS.database.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Document("service")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Service {

    @Id
    String id;
    @NonNull
    String name;
    @NonNull
    Set<UserPermission> users;

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static class UserPermission {
        String user;
        Set<Permission> permissions;
    }
}
