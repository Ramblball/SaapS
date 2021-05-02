package com.example.SappS.database.services;

import com.example.SappS.database.models.Message;
import com.example.SappS.database.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    public Message save(Message message) {
        return messageRepository.save(message);
    }
}
