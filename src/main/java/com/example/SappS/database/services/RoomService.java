package com.example.SappS.database.services;

import com.example.SappS.database.models.Room;
import com.example.SappS.database.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    @Autowired
    RoomRepository roomRepository;

    public Room save(Room room) {
        return roomRepository.save(room);
    }

    public String getChatId(String senderId, String receiverId) {
        return roomRepository.getId(senderId, receiverId)
                .orElseGet(() -> save(new Room(senderId, receiverId)))
                .getId();
    }
}
