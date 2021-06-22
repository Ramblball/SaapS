package com.example.SappS.database.services;

import com.example.SappS.database.models.Room;
import com.example.SappS.database.repositories.RoomRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomService {

    RoomRepository roomRepository;

    public void save(Room room) {
        room = roomRepository.insert(room);
        log.info("Room created: " + room.getId());
    }

    public String getChatId(String senderId, String receiverId) {
        String chatId = senderId + "_" + receiverId;
        save(new Room(chatId, receiverId, senderId));
        save(new Room(chatId, senderId, receiverId));
        return chatId;
    }
}
