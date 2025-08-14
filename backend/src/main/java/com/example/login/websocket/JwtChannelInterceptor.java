package com.example.login.websocket;

import com.example.login.repository.UserRepository;
import com.example.login.security.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.util.List;

@Component
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final JwtService jwt;
    private final UserRepository userRepo;

    public JwtChannelInterceptor(JwtService jwt, UserRepository userRepo) {
        this.jwt = jwt; this.userRepo = userRepo;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        var accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            // Try Authorization header first
            String auth = getFirst(accessor.getNativeHeader(HttpHeaders.AUTHORIZATION));
            String token = null;
            if (StringUtils.hasText(auth) && auth.startsWith("Bearer ")) {
                token = auth.substring(7);
            }

            // Fallback: token header or query param (SockJS may push it as "token")
            if (!StringUtils.hasText(token)) {
                token = getFirst(accessor.getNativeHeader("token"));
            }
            if (!StringUtils.hasText(token)) {
                var qp = accessor.getFirstNativeHeader("query");
                // optional: parse from query string if you pass ?token=...
            }

            if (StringUtils.hasText(token) && jwt.isValid(token)) {
                String username = jwt.extractUsername(token);
                if (userRepo.findByUsername(username).isPresent()) {
                    Principal user = new UsernamePasswordAuthenticationToken(username, null, List.of());
                    accessor.setUser(user);
                } else {
                    throw new IllegalArgumentException("Unknown user");
                }
            } else {
                throw new IllegalArgumentException("Invalid or missing JWT for WebSocket CONNECT");
            }
        }
        return message;
    }

    private static String getFirst(List<String> list) {
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }
}
