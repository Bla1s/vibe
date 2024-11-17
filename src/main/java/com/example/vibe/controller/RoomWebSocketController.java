package com.example.vibe.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class RoomWebSocketController {

    @MessageMapping("/room/{id}/command")
    @SendTo("/topic/room/{id}")
    public CommandResponse handleCommand(CommandRequest commandRequest) {
        CommandResponse response = new CommandResponse();
        String command = HtmlUtils.htmlEscape(commandRequest.getCommand());
        String link = commandRequest.getLink() != null ? HtmlUtils.htmlEscape(commandRequest.getLink()) : null;

        if ("/play".equalsIgnoreCase(command) && link != null) {
            if (!LinkValidator.isValidLink(link) || !LinkValidator.containsValidContent(link)) {
                response.setMessage("Error: Invalid link");
            } else {
                response.setMessage("Playing song from link: " + link);
            }
        } else if ("/pause".equalsIgnoreCase(command)) {
            response.setMessage("Pausing song");
        } else {
            response.setMessage("Unknown command or missing link");
        }

        return response;
    }
}