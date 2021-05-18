package com.example.SappS.database.services;

import com.example.SappS.config.secure.JwtTokenProvider;
import com.example.SappS.database.exceptions.AlreadyExistException;
import com.example.SappS.database.exceptions.NotFoundException;
import com.example.SappS.database.models.User;
import com.example.SappS.database.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    JwtTokenProvider jwtTokenProvider;
    UserRepository userRepository;

    public Optional<String> login(String username, String password) {
        User user = findByName(username)
                .orElseThrow(NotFoundException::new);
        if (user.getPassword().equals(password)) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
            return Optional.of(jwtTokenProvider.generateToken(authentication));
        }
        return Optional.empty();
    }

    public String register(User user) {
        if (userRepository.find("name", user.getName()).isPresent()) {
            throw new AlreadyExistException();
        }
        user = userRepository.save(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword());

        return jwtTokenProvider.generateToken(authentication);
    }

    public Optional<User> findByName(String name) {
        return userRepository.find("name", name);
    }
}
