package com.example.SappS.controllers.services;

import com.example.SappS.controllers.payload.ServiceCreateRequest;
import com.example.SappS.database.exceptions.AlreadyExistException;
import com.example.SappS.database.models.Permission;
import com.example.SappS.database.models.User;
import com.example.SappS.database.services.ServiceService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/service")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ServiceManageController {

    ServiceService serviceService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ServiceCreateRequest request) {
        try {
            String token = serviceService.register(request.getName());
            return ResponseEntity.ok(token);
        } catch (AlreadyExistException ex) {
            log.error(ex.getMessage(), ex);
            return ResponseEntity.badRequest().body("Сервис с таким названием уже существует");
        }
    }

    @GetMapping("/permissions")
    public Set<Permission> getServicePermissions(@RequestHeader String service, @AuthenticationPrincipal User user) {
        return serviceService.getPermissions(service, user.getId());
    }

    @GetMapping("/permissions/all")
    public Permission[] getAllPermissions() {
        return Permission.values();
    }

    @PutMapping("/permissions/add/{permission}")
    public void addPermission(@RequestHeader String service, @AuthenticationPrincipal User user, @PathVariable String permission) {
        Permission newPermission = Permission.valueOf(permission);
        serviceService.addPermission(service, user.getId(), newPermission);
    }


}
