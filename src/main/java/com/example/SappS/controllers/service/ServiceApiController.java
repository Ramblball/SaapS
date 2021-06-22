package com.example.SappS.controllers.service;

import com.example.SappS.database.models.Permission;
import com.example.SappS.database.models.User;
import com.example.SappS.database.services.ServiceService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/service")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ServiceApiController {

    ServiceService serviceService;

    @GetMapping("/permissions")
    public ResponseEntity<?> getServicePermissions(@RequestHeader String service, @AuthenticationPrincipal User user) {
        log.info("/service/permissions GET -> " + service);
        return ResponseEntity.ok(serviceService.getPermissions(service, user.getId()));
    }

    @GetMapping(value = "/list/{field}/{value}")
    @ResponseStatus(HttpStatus.OK)
    private Set<User> getUsersList(@RequestHeader String service, @PathVariable String field, @PathVariable String value) {
        return serviceService.findUsersByCriteria(service, value);
    }


    @PutMapping("/permissions/add/{permission}")
    public void addPermission(@RequestHeader String service, @AuthenticationPrincipal User user, @PathVariable Permission permission) {
        log.info("/permissions/add/" + permission + " -> " + service + " -> processed");
        serviceService.addPermission(service, user.getId(), permission);
        log.info("/permissions/add/" + permission + " -> " + service + " -> updated");
    }

}
