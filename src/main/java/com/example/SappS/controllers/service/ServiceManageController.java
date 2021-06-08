package com.example.SappS.controllers.service;

import com.example.SappS.controllers.payload.ServiceCreateRequest;
import com.example.SappS.database.exceptions.AlreadyExistException;
import com.example.SappS.database.models.Permission;
import com.example.SappS.database.models.User;
import com.example.SappS.database.services.ServiceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/service")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ServiceManageController {

    ServiceService serviceService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ServiceCreateRequest request) {
        try {
            String token = serviceService.register(request.getName());
            log.info("/service/create POST -> " + request.getName() + " -> created");
            return ResponseEntity.ok(token);
        } catch (AlreadyExistException ex) {
            log.error("/service/create POST -> " + request.getName() + " -> " + ex.getMessage(), ex);
            return ResponseEntity.badRequest().body("Сервис с таким названием уже существует");
        }
    }

    @GetMapping("/permissions")
    public ResponseEntity<?> getServicePermissions(@RequestHeader String service, @AuthenticationPrincipal User user) {
        log.info("/service/permissions GET -> " + service);
        return ResponseEntity.ok(serviceService.getPermissions(service, user.getId()));
    }

    @GetMapping("/permissions/all")
    public ResponseEntity<?> getAllPermissions() {
        log.info("/service/permissions/all GET");
        return ResponseEntity.ok(Permission.values());
    }

    @PutMapping("/permissions/add/{permission}")
    public void addPermission(@RequestHeader String service, @AuthenticationPrincipal User user, @PathVariable Permission permission) {
        log.info("/permissions/add/" + permission + " -> " + service + " -> processed");
        serviceService.addPermission(service, user.getId(), permission);
        log.info("/permissions/add/" + permission + " -> " + service + " -> updated");
    }
}
