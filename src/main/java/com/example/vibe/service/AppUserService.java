package com.example.vibe.service;

import com.example.vibe.model.AppUser;
import com.example.vibe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {
    @Autowired
    private UserRepository userRepository;

    public AppUser addUser(AppUser appUser) {
        return userRepository.save(appUser);
    }

    public List<AppUser> getUsersByRoomId(Long roomId) {
        return userRepository.findAll().stream()
                .filter(appUser -> appUser.getRoom().getId().equals(roomId))
                .toList();
    }
}
