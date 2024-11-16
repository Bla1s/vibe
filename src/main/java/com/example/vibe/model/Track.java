package com.example.vibe.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String artist;
    private String source; // "youtube" or "soundcloud"
    private int duration; // in seconds

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}