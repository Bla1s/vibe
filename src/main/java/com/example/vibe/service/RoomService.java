package com.example.vibe.service;

import com.example.vibe.controller.WebSocketController;
import com.example.vibe.model.Room;
import com.example.vibe.model.Song;
import com.example.vibe.repository.RoomRepository;
import com.example.vibe.response.ServerResponse;
import com.example.vibe.util.EventType;
import com.example.vibe.util.RoomNotFoundException;
import com.example.vibe.ytdlpservice.YTDLPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private YTDLPService ytdlpService;

    @Autowired
    private ApplicationContext applicationContext;

    private WebSocketController webSocketController;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
    private final Map<String, ScheduledFuture<?>> roomTimers = new ConcurrentHashMap<>();
    private final Map<String, Long> remainingDurations = new ConcurrentHashMap<>(); // Store remaining durations for each room

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

    private void scheduleNextSongTransition(Room room, long durationMs) {
        String roomId = room.getId();

        // Cancel existing timer
        if (roomTimers.containsKey(roomId)) {
            roomTimers.get(roomId).cancel(false);
        }

        // Schedule new task using proxy to maintain @Transactional behavior
        ScheduledFuture<?> task = scheduler.schedule(() -> {
            RoomService proxy = applicationContext.getBean(RoomService.class);
            proxy.skipToNextSong(room); // Use proxy to ensure transactional behavior
            remainingDurations.remove(roomId);
        }, durationMs, TimeUnit.MILLISECONDS);

        roomTimers.put(roomId, task);
    }

    @Transactional
    public void skipToNextSong(Room room) {
        String roomId = room.getId();
        List<Song> queue = room.getSongQueue();

        // Move to the next song in the queue
        if (!queue.isEmpty()) {
            Song nextSong = queue.removeFirst(); // Remove the first song from the queue
            room.setCurrentSong(nextSong);
            room.setSongStartTime(System.currentTimeMillis());
            room.setCurrentPlayback(0);

            // Schedule transition for the next song
            scheduleNextSongTransition(room, nextSong.getDuration() * 1000);
        } else {
            // No more songs in the queue
            room.setCurrentSong(null);
            room.setPlaying(false);

            // Cancel any pending timer for this room
            if (roomTimers.containsKey(roomId)) {
                roomTimers.get(roomId).cancel(false);
                roomTimers.remove(roomId);
            }
            remainingDurations.remove(roomId);
        }

        roomRepository.save(room);
        // Build response with the updated room state
        ServerResponse response = new ServerResponse(
                EventType.SKIP,
                null, // Room-level info is unnecessary; focus on song data
                Map.of(
                        "currentSong", room.getCurrentSong(),
                        "currentPlayback", room.getCurrentPlayback(),
                        "isPlaying", room.isPlaying(),
                        "songQueue", room.getSongQueue()
                ),
                "Skipped to next song"
        );

        // Notify WebSocket clients (assuming WebSocketController or similar exists)
        webSocketController.sendRoomUpdate(roomId, response);  // This line pushes the update
    }


    public ServerResponse playSong(String roomId) {
        Room room = findRoomOrThrow(roomId);

        if (room.getCurrentSong() == null) {
            if (!room.getSongQueue().isEmpty()) {
                Song nextSong = room.getSongQueue().removeFirst();
                room.setCurrentSong(nextSong);
                room.setSongStartTime(System.currentTimeMillis());
                room.setCurrentPlayback(0);
                room.setPlaying(true);

                scheduleNextSongTransition(room, nextSong.getDuration() * 1000);
            } else {
                throw new IllegalStateException("No song in queue to play");
            }
        } else {
            long playbackStartTime = System.currentTimeMillis() - room.getCurrentPlayback();
            room.setSongStartTime(playbackStartTime);
            room.setPlaying(true);

            // Reschedule the song transition
            long remainingTime = remainingDurations.getOrDefault(roomId,
                    room.getCurrentSong().getDuration() * 1000 - room.getCurrentPlayback());
            scheduleNextSongTransition(room, remainingTime);
        }

        roomRepository.save(room);

        return new ServerResponse(
                EventType.PLAY,
                null,
                Map.of(
                        "currentSong", room.getCurrentSong(),
                        "currentPlayback", room.getCurrentPlayback(),
                        "isPlaying", room.isPlaying()
                ),
                "Playing song"
        );
    }
    public ServerResponse pauseSong(String roomId) {
        Room room = findRoomOrThrow(roomId);

        if (room.isPlaying()) {
            long elapsedTime = System.currentTimeMillis() - room.getSongStartTime();
            room.setCurrentPlayback(elapsedTime);
            room.setPlaying(false);

            // Cancel the current timer
            if (roomTimers.containsKey(roomId)) {
                roomTimers.get(roomId).cancel(false);
                roomTimers.remove(roomId);
            }

            // Save the remaining time
            long remainingTime = room.getCurrentSong().getDuration() * 1000 - elapsedTime;
            remainingDurations.put(roomId, remainingTime);

            roomRepository.save(room);
        }

        return new ServerResponse(
                EventType.PAUSE,
                null,
                Map.of(
                        "currentSong", room.getCurrentSong(),
                        "currentPlayback", room.getCurrentPlayback(),
                        "isPlaying", room.isPlaying()
                ),
                "Paused song"
        );
    }


}