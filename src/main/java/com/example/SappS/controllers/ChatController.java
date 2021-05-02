package com.example.SappS.controllers;

import com.example.SappS.database.models.Message;
import com.example.SappS.database.services.MessageService;
import com.example.SappS.database.services.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private MessageService messageService;
    @Autowired
    private RoomService roomService;

    @MessageMapping("/chat")
    public void processMessage(@Payload Message message) {
        String chatId = roomService.getChatId(message.getSenderId(), message.getReceiverId());
        message.setChatId(chatId);

        Message saved = messageService.save(message);

        messagingTemplate.convertAndSendToUser(
                message.getReceiverId(), "/queue/messages",
                new ChatNotification(saved.getId(), saved.getSenderId(), saved.getSenderName())
        );
    }

    @AllArgsConstructor
    private static class ChatNotification {
        private final String id;
        private final String senderId;
        private final String senderName;
    }
}
