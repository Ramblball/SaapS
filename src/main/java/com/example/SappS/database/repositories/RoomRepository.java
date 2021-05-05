package com.example.SappS.database.repositories;

import com.example.SappS.database.models.Room;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Slf4j
@Repository
public class RoomRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Room save(Room room) {
        mongoTemplate.save(room);
        log.info("Room saved");
        log.info("room: " + room);
        return room;
    }

    public Optional<Room> find(String criteria, String value) {
        Query query = new Query(where(criteria).is(value));

        Room result = mongoTemplate.findOne(query, Room.class);
        if (result == null) {
            return Optional.empty();
        }
        log.info("Room founded");
        log.info("room: " + result);
        return Optional.of(result);
    }

    public Optional<Room> getId(String firstId, String secondId) {
        Query firstQuery = new Query(
                where("firstId").is(firstId).and("secondId").is(secondId));
        Query secondQuery = new Query(
                where("firstId").is(secondId).and("secondId").is(firstId));
        Room result = mongoTemplate.findOne(firstQuery, Room.class);
        if (result == null) {
            result = mongoTemplate.findOne(secondQuery, Room.class);
            if (result == null) {
                return Optional.empty();
            }
        }
        log.info("Room founded");
        log.info("room: " + result);
        return Optional.of(result);
    }
}
