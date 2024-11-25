package com.example.vibe.controller;

import com.example.vibe.model.Room;
import com.example.vibe.model.Song;
import com.example.vibe.model.User;
import com.example.vibe.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
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
    @MessageMapping("/room/{roomId}/next")
    @SendTo("/topic/room/{roomId}")
    public Room playNextSong(@DestinationVariable String roomId) {
        return roomService.playNextSong(roomId);
    }

    @MessageMapping("/room/{roomId}/addSong")
    @SendTo("/topic/room/{roomId}")
    public Room addSong(@DestinationVariable String roomId, @Payload String songUrl) {
        try {
            return roomService.addSong(roomId, songUrl);
        } catch (IllegalArgumentException e) {
            // Send an error message to the client
            throw new MessagingException("Invalid song URL");
        }
    }
    @MessageMapping("/room/{roomId}/state")
    @SendTo("/topic/room/{roomId}")
    public Room getRoomState(@DestinationVariable String roomId) {
        return roomService.getRoomState(roomId);
    }

}
