package com.example.SappS.database.repositories;

import com.example.SappS.database.models.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {

    Optional<Message> findById(String id);
}
