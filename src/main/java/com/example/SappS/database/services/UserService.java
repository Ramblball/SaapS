package com.example.SappS.database.services;

import com.example.SappS.config.secure.JwtTokenProvider;
import com.example.SappS.database.exceptions.AlreadyExistException;
import com.example.SappS.database.exceptions.NotFoundException;
import com.example.SappS.database.models.User;
import com.example.SappS.database.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    JwtTokenProvider jwtTokenProvider;
    UserRepository userRepository;

    public Optional<String> login(String username, String password) {
        User user = userRepository.findByName(username)
                .orElseThrow(NotFoundException::new);
        log.info("User found: " + user.getId());
        if (user.getPassword().equals(password)) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
            log.info("User authorized: " + user.getId());
            return Optional.of(jwtTokenProvider.generateToken(authentication));
        }
        return Optional.empty();
    }

    public String register(User user) {
        if (userRepository.findByName(user.getName()).isPresent()) {
            throw new AlreadyExistException();
        }
        user = userRepository.insert(user);
        log.info("User saved: " + user.getId());
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword());

        return jwtTokenProvider.generateToken(authentication);
    }

    public User findByName(String name) {
        Optional<User> user = userRepository.findByName(name);
        if (user.isEmpty()) {
            throw new NotFoundException("User " + name + " not found");
        }
        log.info("User found: " + user);
        return user.get();
    }
}
