package com.example.SappS.database.services;

import static com.example.SappS.database.models.Service.UserPermission;

import com.example.SappS.database.exceptions.AlreadyExistException;
import com.example.SappS.database.exceptions.NotFoundException;
import com.example.SappS.database.models.Permission;
import com.example.SappS.database.models.Service;
import com.example.SappS.database.models.User;
import com.example.SappS.database.repositories.ServiceRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@org.springframework.stereotype.Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ServiceService {

    ServiceRepository serviceRepository;
    UserService userService;

    public String register(String name) throws AlreadyExistException{
        Service service = new Service(name, new HashSet<>());

        if (serviceRepository.findByName(service.getName()).isPresent()) {
            throw new AlreadyExistException();
        }
        service = serviceRepository.insert(service);
        log.info("Service created: " + service.getId());

        return service.getId();
    }

    public Service findById(String id) {
        return serviceRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    public Set<Permission> getPermissions(String id, String user) {
        Service service = findById(id);
        log.info("Service found: " + service.getId());
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
        log.info("Service found: " + service.getId());
        Optional<UserPermission> userPermission =
                service.getUsers()
                        .stream()
                        .filter(p -> p.getUser().equals(user))
                        .findFirst();
        if (userPermission.isEmpty()) {
            Set<Permission> permissions = new HashSet<>();
            permissions.add(newPermission);
            service.getUsers().add(new UserPermission(user, permissions));
        } else {
            userPermission.get().getPermissions().add(newPermission);
        }
        serviceRepository.save(service);
        log.info("Service updated: " + service.getId());
    }

    public Set<User> findUsersByCriteria(String id, String value) {
        Service service = findById(id);
        log.info("Service found: " + service.getId());
        return service.getUsers().stream()
                .filter(userPermission -> userPermission.getPermissions().contains(Permission.LOCATION))
                .map(userPermission ->
                        userService.findByIdAndCity(userPermission.getUser(), value))
                .collect(Collectors.toSet());
    }
}
