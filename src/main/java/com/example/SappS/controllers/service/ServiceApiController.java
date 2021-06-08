package com.example.SappS.controllers.service;

import com.example.SappS.database.models.User;
import com.example.SappS.database.services.ServiceService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/service")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ServiceApiController {

    ServiceService serviceService;

    @GetMapping(value = "/list/{field}/{value}")
    @ResponseStatus(HttpStatus.OK)
    private Set<User> getUsersList(@RequestHeader String service, @PathVariable String field, @PathVariable String value) {
        return serviceService.findUsersByCriteria(service, value);
    }
}
