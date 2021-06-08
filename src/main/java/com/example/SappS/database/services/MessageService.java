package com.example.SappS.database.services;

import com.example.SappS.database.models.Message;
import com.example.SappS.database.repositories.MessageRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageService {

    MessageRepository messageRepository;

    public Message save(Message message) {
        message = messageRepository.insert(message);
        log.info("Message created: " + message.getId());
        return message;
    }
}
