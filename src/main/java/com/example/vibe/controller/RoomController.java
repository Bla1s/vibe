package com.example.vibe.controller;

import com.example.vibe.model.Room;
import com.example.vibe.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @PostMapping
    public Room createRoom(@RequestBody Room room) {
        return roomService.createRoom(room);
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable String id) {
        return roomService.getRoomById(id);
    }

    @PostMapping("/{id}/command")
    public void handleCommand(@PathVariable String id, @RequestBody CommandRequest commandRequest) {
        log.info("Received command request for room " + id);
        String command = commandRequest.getCommand();
        String link = commandRequest.getLink();

        log.info("Command: " + command + ", Link: " + link);

        switch (command.toLowerCase()) {
            case "/play":
                if (link != null && !link.isEmpty()) {
                    log.info("Playing song from link: " + link + " in room " + id);
                    // Add logic to play the song from the link
                } else {
                    log.warn("Link is required for /play command");
                }
                break;
            case "/pause":
                log.info("Pausing song in room " + id);
                // Add logic to pause the current song
                break;
            default:
                log.warn("Unknown command: " + command);
        }
    }
}