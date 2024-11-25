package com.example.vibe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Room {
    @Id
    private String id;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Song> songQueue = new ArrayList<>();

    private long songStartTime; // Timestamp when the current song started
    private long currentPlayback; // Current playback time in milliseconds
    private long responseTimestamp; // Timestamp of the server response
    private boolean isPlaying;

    public Room(String id) {
        this.id = id != null ? id : UUID.randomUUID().toString();
    }

    public void addUser(User user) {
        users.add(user);
        user.setRoom(this);
    }

    public void removeUser(User user) {
        users.remove(user);
        user.setRoom(null);
    }

    public void addSong(Song song) {
        songQueue.add(song);
        song.setRoom(this);
    }
    public void removeCurrentSong() {
        if (!songQueue.isEmpty()) {
            songQueue.removeFirst();
        }
    }
}