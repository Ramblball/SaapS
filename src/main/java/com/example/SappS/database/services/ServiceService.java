package com.example.SappS.database.services;

import com.example.SappS.config.secure.JwtTokenProvider;
import com.example.SappS.database.exceptions.AlreadyExistException;
import com.example.SappS.database.exceptions.NotFoundException;
import com.example.SappS.database.models.Permission;
import com.example.SappS.database.models.Service;
import com.example.SappS.database.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private ServiceRepository serviceRepository;

    public String register(String name) {
        Service service = new Service(name, new ArrayList<>());
        
        if (serviceRepository.find("name", service.getName()).isPresent()) {
            throw new AlreadyExistException();
        }
        service = serviceRepository.save(service);
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(service.getName(), ""));

        return jwtTokenProvider.generateToken(authentication);
    }

    public Service findById(String id) {
        return serviceRepository.find("id", id)
                .orElseThrow(NotFoundException::new);
    }

    public Service findByName(String name) {
        return serviceRepository.find("name", name)
                .orElseThrow(NotFoundException::new);
    }

    public void addPermission(String id, Permission permission) {
        Service service = findById(id);
        List<Permission> servicePermission = service.getPermissions();
        servicePermission.add(permission);

        serviceRepository.update("id", id, "permissions", servicePermission.toString());
    }
}
