package com.example.vibe.response;

import com.example.vibe.model.Room;
import com.example.vibe.util.EventType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServerResponse {
    private EventType eventType; // Type of event (e.g., PLAY, PAUSE)
    private Room room;           // Room-related state
    private Object payload;      // Additional data (flexible)
    private String message;      // Optional message (for errors, notifications, etc.)

    public ServerResponse(EventType eventType, Room room, Object payload, String message) {
        this.eventType = eventType;
        this.room = room;
        this.payload = payload;
        this.message = message;
    }
}
