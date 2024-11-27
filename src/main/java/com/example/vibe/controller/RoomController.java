package com.example.vibe.controller;

import com.example.vibe.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/room/{roomId}")
    public String room(@PathVariable String roomId, Model model) {
        try {
            if (!roomService.roomExists(roomId)) {
                model.addAttribute("errorMessage", "Room not found");
                return "error/404";
            }
            model.addAttribute("roomId", roomId);
            return "room";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred while accessing the room");
            return "error/404";
        }
    }
}