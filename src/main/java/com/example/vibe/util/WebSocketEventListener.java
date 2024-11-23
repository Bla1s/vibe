package com.example.vibe.util;

import com.example.vibe.model.User;
import com.example.vibe.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    @Autowired
    private RoomService roomService;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");
        User user = (User) headerAccessor.getSessionAttributes().get("user");

        if (roomId != null && user != null) {
            roomService.removeUserFromRoom(roomId, user);
        }
    }
}