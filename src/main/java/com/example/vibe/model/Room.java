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


    @ManyToOne
    @JoinColumn(name = "current_song_id")
    private Song currentSong;
    private long songStartTime;
    private long currentPlayback;
    private boolean isPlaying;

    public Room(String id) {
        this.id = id != null ? id : UUID.randomUUID().toString();
    }

}