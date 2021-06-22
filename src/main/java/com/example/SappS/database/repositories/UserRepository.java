package com.example.SappS.database.repositories;

import com.example.SappS.database.models.User;
import lombok.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findById(String id);

    Optional<User> findByName(String name);

    @Query(fields = "{ 'password': 0 }")
    Optional<User> findByIdAndCity(String id, @NonNull String city);
}
