package com.example.vibe.controller;

import com.example.vibe.model.Room;
import com.example.vibe.model.User;
import com.example.vibe.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    @Autowired
    private RoomService roomService;

    @MessageMapping("/room/{roomId}/join")
    @SendTo("/topic/room/{roomId}")
    public Room joinRoom(@DestinationVariable String roomId, User user, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("roomId", roomId);
        headerAccessor.getSessionAttributes().put("user", user);
        return roomService.addUserToRoom(roomId, user);
    }

    @MessageMapping("/room/{roomId}/leave")
    @SendTo("/topic/room/{roomId}")
    public Room leaveRoom(@DestinationVariable String roomId, User user) {
        return roomService.removeUserFromRoom(roomId, user);
    }

    @MessageMapping("/room/{roomId}/toggle")
    @SendTo("/topic/room/{roomId}")
    public Room togglePlayback(@DestinationVariable String roomId) {
        return roomService.togglePlayback(roomId);
    }

    @MessageMapping("/room/{roomId}/skip")
    @SendTo("/topic/room/{roomId}")
    public Room skipSong(@DestinationVariable String roomId) {
        return roomService.skipSong(roomId);
    }
}
