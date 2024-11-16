package com.example.vibe.service;

import com.example.vibe.model.Track;
import com.example.vibe.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackService {
    @Autowired
    private TrackRepository trackRepository;

    public Track addTrack(Track track) {
        return trackRepository.save(track);
    }

    public List<Track> getTracksByRoomId(Long roomId) {
        return trackRepository.findAll().stream()
                .filter(track -> track.getRoom().getId().equals(roomId))
                .toList();
    }
}
