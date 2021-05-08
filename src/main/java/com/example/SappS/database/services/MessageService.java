package com.example.SappS.database.services;

import com.example.SappS.database.models.Message;
import com.example.SappS.database.repositories.MessageRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageService {

    MessageRepository messageRepository;

    public Message save(Message message) {
        return messageRepository.save(message);
    }
}
