package com.example.SappS.controllers;

import com.example.SappS.controllers.payload.UserResponse;
import com.example.SappS.database.models.User;
import com.example.SappS.database.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    UserService userService;

    @GetMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    private UserResponse getCurrentUser(@AuthenticationPrincipal User user) {
        return new UserResponse(user.getId(), user.getName(), user.getAge(), user.getCity());
    }

    @GetMapping(value = "/list/{city}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    private List<User> getUsersList(@PathVariable String city) {
        return userService.findUsersByCity(city);
    }
}
