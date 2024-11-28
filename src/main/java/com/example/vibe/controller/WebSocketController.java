package com.example.vibe.controller;

import com.example.vibe.model.Room;
import com.example.vibe.model.Song;
import com.example.vibe.model.User;
import com.example.vibe.response.ServerResponse;
import com.example.vibe.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    @Autowired
    private RoomService roomService;

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendRoomUpdate(String roomId, ServerResponse response) {
        // Sends the response to clients subscribed to the specific room topic
        messagingTemplate.convertAndSend("/topic/room/" + roomId, response);
    }

}
