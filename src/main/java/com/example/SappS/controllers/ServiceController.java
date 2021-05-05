package com.example.SappS.controllers;

import com.example.SappS.controllers.payload.JwtAuthenticationResponse;
import com.example.SappS.controllers.payload.ServiceCreateRequest;
import com.example.SappS.database.models.Permission;
import com.example.SappS.database.models.Service;
import com.example.SappS.database.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ServiceCreateRequest request) {
        String token = serviceService.register(request.getName());
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @GetMapping("/permissions")
    public List<Permission> getServicePermissions(@AuthenticationPrincipal Service service) {
        return service.getPermissions();
    }

    @GetMapping("/permissions/all")
    public Permission[] getAllPermissions() {
        return Permission.values();
    }

    @PutMapping("/permissions/add/{permission}")
    public void addPermission(@AuthenticationPrincipal Service service, @PathVariable String permission) {
        Permission newPermission = Permission.valueOf(permission);
        serviceService.addPermission(service.getId(), newPermission);
    }


}
