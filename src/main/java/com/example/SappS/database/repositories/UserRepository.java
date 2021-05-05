package com.example.SappS.database.repositories;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.example.SappS.database.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class UserRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public User save(User user) {
        mongoTemplate.save(user);
        log.info("User saved");
        log.info("user: " + user);
        return user;
    }

    public Optional<User> find(String criteria, String value) {
        Query query = new Query(where(criteria).is(value));
        User result = mongoTemplate.findOne(query, User.class);
        if (result == null) {
            return Optional.empty();
        }
        log.info("User found");
        log.info("user: " + result);
        return Optional.of(result);
    }

    public void update(String criteria, String value, String updateCriteria, String updateValue) {
        Query query = new Query(where(criteria).is(value));

        mongoTemplate.updateFirst(query, Update.update(updateCriteria, updateValue), User.class);
        log.info("User updated");
    }

    public List<User> findAll(MongoOperations operations) {
        List<User> listUser = mongoTemplate.findAll(User.class);
        listUser.forEach(u -> log.info("User: " + u));
        return listUser;
    }

    public void remove(String criteria, String value) {
        Query query = new Query(where(criteria).is(value));

        mongoTemplate.remove(query, User.class);
        log.info("User removed");
    }
}
