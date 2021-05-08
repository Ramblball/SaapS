package com.example.SappS.database.services;

import com.example.SappS.config.secure.JwtTokenProvider;
import com.example.SappS.database.exceptions.AlreadyExistException;
import com.example.SappS.database.models.User;
import com.example.SappS.database.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    UserRepository userRepository;

    public String login(String username, String password) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));

        return jwtTokenProvider.generateToken(authentication);
    }

    public String register(User user) {
        if (userRepository.find("name", user.getName()).isPresent()) {
            throw new AlreadyExistException();
        }
        user = userRepository.save(user);
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));

        return jwtTokenProvider.generateToken(authentication);
    }

    public Optional<User> findByName(String name) {
        return userRepository.find("name", name);
    }
}
