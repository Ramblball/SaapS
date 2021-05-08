package com.example.SappS.database.repositories;

import com.example.SappS.database.models.Service;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ServiceRepository {

    MongoTemplate mongoTemplate;

    public Service save(Service service) {
        service = mongoTemplate.save(service);
        log.info("Service created");
        log.info("service: " + service);
        return service;
    }

    public Optional<Service> find(String criteria, String value) {
        Query query = new Query(where(criteria).is(value));
        Service result = mongoTemplate.findOne(query, Service.class);
        if (result == null) {
            return Optional.empty();
        }
        log.info("Service found");
        log.info("service: " + result);
        return Optional.of(result);
    }

    public void update(String criteria, String value, String updateCriteria, Object updateValue) {
        Query query = new Query(where(criteria).is(value));

        mongoTemplate.updateFirst(query, Update.update(updateCriteria, updateValue), Service.class);
        log.info("Service updated");
    }
}
