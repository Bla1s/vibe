package com.example.vibe.controller;

import com.example.vibe.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/create-room")
    public String createRoom(RedirectAttributes redirectAttributes) {
        String roomId = roomService.createRoom();
        return "redirect:/room/" + roomId;
    }
}