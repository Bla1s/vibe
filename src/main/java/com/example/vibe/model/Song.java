package com.example.vibe.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String url;
    private String addedBy;
    @Column(length = 2048)
    private String streamUrl;
    private long duration;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonBackReference
    private Room room;
}