package com.example.vibe.service;

import com.example.vibe.model.Room;
import com.example.vibe.model.User;
import com.example.vibe.repository.RoomRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public String createRoom() {
        String roomId = UUID.randomUUID().toString();
        Room room = new Room(roomId);
        roomRepository.save(room);
        return roomId;
    }

    public boolean roomExists(String roomId) {
        return roomRepository.existsById(roomId);
    }

    @Transactional
    public Room addUserToRoom(String roomId, User user) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        Hibernate.initialize(room.getUsers());
        room.addUser(user);
        return roomRepository.save(room);
    }
    @Transactional
    public Room removeUserFromRoom(String roomId, User user) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        Hibernate.initialize(room.getUsers());
        room.removeUser(user);
        return roomRepository.save(room);
    }

    public Room togglePlayback(String roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        room.setPlaying(!room.isPlaying());
        return roomRepository.save(room);
    }
    @Transactional
    public Room skipSong(String roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        if (!room.getSongQueue().isEmpty()) {
            room.getSongQueue().removeFirst();
        }
        return roomRepository.save(room);
    }
}