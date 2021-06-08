package com.example.SappS.database.repositories;

import com.example.SappS.database.models.Service;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceRepository extends MongoRepository<Service, String> {

    Optional<Service> findById(String id);

    Optional<Service> findByName(String name);
}
