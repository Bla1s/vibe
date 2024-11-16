package com.example.vibe.controller;

import com.example.vibe.model.AppUser;
import com.example.vibe.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class AppUserController {
    @Autowired
    private AppUserService appUserService;

    @PostMapping
    public AppUser addUser(@RequestBody AppUser appUser) {
        return appUserService.addUser(appUser);
    }

    @GetMapping("/room/{roomId}")
    public List<AppUser> getUsersByRoomId(@PathVariable Long roomId) {
        return appUserService.getUsersByRoomId(roomId);
    }
}
