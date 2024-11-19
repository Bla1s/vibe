package com.example.vibe.controller;

import com.example.vibe.model.Track;
import com.example.vibe.service.RoomService;
import com.example.vibe.util.LinkParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.HtmlUtils;

@Controller
public class RoomWebSocketController {

    @Autowired
    private RoomService roomService;

    @MessageMapping("/room/{id}/command")
    @SendTo("/topic/room/{id}")
    public CommandResponse handleCommand(@PathVariable String id, CommandRequest commandRequest) {
        CommandResponse response = new CommandResponse();
        String command = HtmlUtils.htmlEscape(commandRequest.getCommand());
        String link = commandRequest.getLink() != null ? HtmlUtils.htmlEscape(commandRequest.getLink()) : null;

        if ("/play".equalsIgnoreCase(command) && link != null) {
            try {
                Track track = LinkParser.parseYouTubeLink(link);
                roomService.addTrackToRoom(id, track);
                response.setMessage("Added song to queue: " + track.getTitle());
            } catch (Exception e) {
                response.setMessage("Error: " + e.getMessage());
            }
        } else if ("/pause".equalsIgnoreCase(command)) {
            response.setMessage("Pausing song");
        } else {
            response.setMessage("Unknown command or missing link");
        }

        return response;
    }
}