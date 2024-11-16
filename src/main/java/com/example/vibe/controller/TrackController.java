package com.example.vibe.controller;

import com.example.vibe.model.Track;
import com.example.vibe.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tracks")
public class TrackController {
    @Autowired
    private TrackService trackService;

    @PostMapping
    public Track addTrack(@RequestBody Track track) {
        return trackService.addTrack(track);
    }

    @GetMapping("/room/{roomId}")
    public List<Track> getTracksByRoomId(@PathVariable Long roomId) {
        return trackService.getTracksByRoomId(roomId);
    }
}
