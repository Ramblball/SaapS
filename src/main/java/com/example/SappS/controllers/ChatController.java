package com.example.SappS.controllers;

import com.example.SappS.controllers.payload.UserResponse;
import com.example.SappS.database.exceptions.NotFoundException;
import com.example.SappS.database.models.Message;
import com.example.SappS.database.models.User;
import com.example.SappS.database.services.MessageService;
import com.example.SappS.database.services.RoomService;
import com.example.SappS.database.services.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatController {

    SimpMessagingTemplate messagingTemplate;
    MessageService messageService;
    UserService userService;
    RoomService roomService;

    @MessageMapping("/chat")
    public void processMessage(@Payload Message message) {
        message.setChatId(roomService.getChatId(message.getSenderId(), message.getReceiverId()));

        Message saved = messageService.save(message);

        messagingTemplate.convertAndSendToUser(
                message.getReceiverId(), "/queue/messages", saved);
    }

    @GetMapping(value = "/friend/{name}")
    public ResponseEntity<?> getFriendUser(@PathVariable String name) {
        Optional<User> friend = userService.findByName(name);
        if (friend.isEmpty()) {
            throw new NotFoundException("User " + name + " not found");
        }
        User user = friend.get();
        UserResponse response = new UserResponse(user.getId(), user.getName(), user.getAge(), user.getCity());
        return ResponseEntity.ok(response);
    }
}
