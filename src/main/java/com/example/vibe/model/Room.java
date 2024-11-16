package com.example.vibe.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Data
@Entity
public class Room {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private String id;
    private String name;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Track> tracks;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<AppUser> users;
}