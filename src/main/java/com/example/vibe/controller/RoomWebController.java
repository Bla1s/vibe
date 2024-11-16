package com.example.vibe.controller;

import com.example.vibe.model.Room;
import com.example.vibe.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RoomWebController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/room/{id}")
    public String getRoomById(@PathVariable String id, Model model) {
        Room room = roomService.getRoomById(id);
        if (room != null) {
            model.addAttribute("room", room);
            return "room";
        } else {
            return "error/404";
        }
    }
}
