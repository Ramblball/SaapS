package com.example.SappS.database.services;

import static com.example.SappS.database.models.Service.UserPermission;

import com.example.SappS.database.exceptions.AlreadyExistException;
import com.example.SappS.database.exceptions.NotFoundException;
import com.example.SappS.database.models.Permission;
import com.example.SappS.database.models.Service;
import com.example.SappS.database.models.User;
import com.example.SappS.database.repositories.ServiceRepository;
import com.example.SappS.database.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@org.springframework.stereotype.Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ServiceService {

    ServiceRepository serviceRepository;
    UserRepository userRepository;

    public String register(String name) {
        Service service = new Service(name, new HashSet<>());

        if (serviceRepository.find("name", service.getName()).isPresent()) {
            throw new AlreadyExistException();
        }
        service = serviceRepository.save(service);

        return service.getId();
    }

    public Service findById(String id) {
        return serviceRepository.find("id", id)
                .orElseThrow(NotFoundException::new);
    }

    public Service findByName(String name) {
        return serviceRepository.find("name", name)
                .orElseThrow(NotFoundException::new);
    }

    public Set<Permission> getPermissions(String id, String user) {
        Service service = findById(id);
        Optional<UserPermission> permission = service.getUsers().
                stream()
                .filter(p -> p.getUser().equals(user))
                .findFirst();
        if (permission.isEmpty()) {
            return new HashSet<>();
        }
        return permission.get().getPermissions();
    }

    public void addPermission(String id, String user, Permission newPermission) {
        Service service = findById(id);
        boolean empty = service.getUsers().
                stream()
                .filter(p -> p.getUser().equals(user))
                .findFirst()
                .isEmpty();
        if (empty) {
            service.getUsers().add(new UserPermission(user, new HashSet<>()));
        }
        Set<UserPermission> permission = service.getUsers().
                stream()
                .peek(p -> {
                    if (p.getUser().equals(user)) {
                        p.getPermissions().add(newPermission);
                    }
                }).collect(Collectors.toSet());

        serviceRepository.update("id", id, "users", permission);
    }

    public Set<User> findUsersByCriteria(String id, String field, String value) {
        Service service = findById(id);
        return service.getUsers().stream()
                .filter(userPermission -> userPermission.getPermissions().contains(Permission.LOCATION))
                .map(userPermission ->
                        userRepository.findByIdAndCriteria(userPermission.getUser(), field, value).orElse(null))
                .collect(Collectors.toSet());
    }
}
