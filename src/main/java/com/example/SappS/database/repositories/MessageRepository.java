package com.example.SappS.database.repositories;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import com.example.SappS.database.models.Message;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
public class MessageRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Message save(Message message) {
        mongoTemplate.save(message);
        log.info("Message saved");
        log.info("message: " + message);
        return message;
    }

    public Optional<Message> find(String criteria, String value) {
        Query query = new Query(where(criteria).is(value));

        Message result = mongoTemplate.findOne(query, Message.class);
        if (result == null) {
            return Optional.empty();
        }
        log.info("Message founded");
        log.info("message: " + result);
        return Optional.of(result);
    }
}
