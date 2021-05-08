package com.example.SappS.database.services;

import com.example.SappS.database.models.Room;
import com.example.SappS.database.repositories.RoomRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomService {

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
