package com.example.SappS.controllers;

import com.example.SappS.controllers.payload.JwtAuthenticationResponse;
import com.example.SappS.controllers.payload.LoginRequest;
import com.example.SappS.database.models.User;
import com.example.SappS.database.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String token = userService.login(request.getName(), request.getPassword());
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody User request) {
        String token = userService.register(request);
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }
}
