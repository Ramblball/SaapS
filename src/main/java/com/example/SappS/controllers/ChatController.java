package com.example.SappS.controllers;

import com.example.SappS.controllers.payload.MessageRequest;
import com.example.SappS.database.models.Message;
import com.example.SappS.database.models.User;
import com.example.SappS.database.services.MessageService;
import com.example.SappS.database.services.RoomService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatController {

    SimpMessagingTemplate messagingTemplate;
    MessageService messageService;
    RoomService roomService;

    @MessageMapping("/chat")
    public void processMessage(@Payload MessageRequest messageRequest, @AuthenticationPrincipal User user) {
        Message message = Message.builder()
                .chatId(roomService.getChatId(user.getId(), messageRequest.getReceiverId()))
                .senderId(user.getId())
                .senderName(user.getName())
                .receiverId(messageRequest.getReceiverId())
                .receiverName(messageRequest.getReceiverName())
                .message(messageRequest.getMessage())
                .build();

        Message saved = messageService.save(message);

        messagingTemplate.convertAndSendToUser(
                message.getReceiverId(), "/queue/messages",
                new ChatNotification(saved.getId(), saved.getSenderId(), saved.getSenderName())
        );
    }

    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    private static class ChatNotification {
        String id;
        String senderId;
        String senderName;
    }
}
