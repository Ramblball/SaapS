package com.example.SappS.controllers;

import com.example.SappS.database.models.Message;
import com.example.SappS.database.services.MessageService;
import com.example.SappS.database.services.RoomService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatController {

    SimpMessagingTemplate messagingTemplate;
    MessageService messageService;
    RoomService roomService;

    @MessageMapping("/chat")
    public void processMessage(@Payload Message message) {
        message.setChatId(roomService.getChatId(message.getSenderId(), message.getReceiverId()));

        Message saved = messageService.save(message);

        messagingTemplate.convertAndSendToUser(
                message.getReceiverId(), "/queue/messages", saved);
    }
}
