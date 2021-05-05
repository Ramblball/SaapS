package com.example.SappS.controllers.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class LoginRequest {
    private final String name;
    private final String password;
}
