package com.example.login.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    // Client sends to /app/chat.send ; we broadcast to /topic/public
    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public ChatMessage send(@Payload String content, SimpMessageHeaderAccessor headers) {
        String username = headers.getUser() != null ? headers.getUser().getName() : "anon";
        return new ChatMessage(username, content);
    }
}
