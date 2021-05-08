package com.example.SappS.controllers;

import com.example.SappS.controllers.payload.JwtAuthenticationResponse;
import com.example.SappS.controllers.payload.LoginRequest;
import com.example.SappS.database.models.User;
import com.example.SappS.database.services.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<String> token = userService.login(request.getName(), request.getPassword());
        if (token.isPresent()) {
            return ResponseEntity.ok(new JwtAuthenticationResponse(token.get()));
        }
        return ResponseEntity.badRequest().body("Неверный пароль");
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody User request) {
        String token = userService.register(request);
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }
}
