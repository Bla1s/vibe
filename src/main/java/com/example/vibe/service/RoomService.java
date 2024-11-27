package com.example.vibe.service;

import com.example.vibe.model.Room;
import com.example.vibe.repository.RoomRepository;
import com.example.vibe.util.RoomNotFoundException;
import com.example.vibe.ytdlpservice.YTDLPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private YTDLPService ytdlpService;

    public String createRoom() {
        String roomId = UUID.randomUUID().toString();
        Room room = new Room(roomId);
        roomRepository.save(room);
        return roomId;
    }

    public boolean roomExists(String roomId) {
        return roomRepository.existsById(roomId);
    }
    private Room findRoomOrThrow(String roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room with ID " + roomId + " not found"));
    }
    //TODO / FIX


}