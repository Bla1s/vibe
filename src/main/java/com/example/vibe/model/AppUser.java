package com.example.vibe.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}