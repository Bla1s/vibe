package com.example.vibe.service;

import com.example.vibe.model.Room;
import com.example.vibe.model.Track;
import com.example.vibe.repository.RoomRepository;
import com.example.vibe.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private TrackRepository trackRepository;

    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(String id) {
        Optional<Room> room = roomRepository.findById(id);
        return room.orElse(null);
    }

    public void addTrackToRoom(String roomId, Track track) {
        Room room = getRoomById(roomId);
        if (room != null) {
            track.setRoom(room);
            trackRepository.save(track);
        }
    }
}