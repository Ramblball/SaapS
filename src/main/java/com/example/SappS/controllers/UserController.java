package com.example.SappS.controllers;

import com.example.SappS.controllers.payload.UserResponse;
import com.example.SappS.database.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    private UserResponse getCurrentUser(@AuthenticationPrincipal User user) {
        return new UserResponse(user.getId(), user.getName(), user.getAge(), user.getCity());
    }
}
