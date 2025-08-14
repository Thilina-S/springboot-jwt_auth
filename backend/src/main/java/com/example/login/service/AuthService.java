package com.example.login.service;

import com.example.login.model.User;
import com.example.login.payload.LoginRequest;
import com.example.login.payload.RegisterRequest;
import com.example.login.payload.UserDto;
import com.example.login.repository.UserRepository;
import com.example.login.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public AuthService(UserRepository repo, PasswordEncoder encoder, JwtService jwt) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    public void register(RegisterRequest req) {
        if (repo.existsByUsername(req.username()))
            throw new IllegalArgumentException("Username already used");
        if (repo.existsByEmail(req.email()))
            throw new IllegalArgumentException("Email already used");

        User user = new User();
        user.setUsername(req.username());
        user.setEmail(req.email());
        user.setPassword(encoder.encode(req.password()));

        repo.save(user);
    }

    public String login(LoginRequest req) {
        User user = repo.findByUsername(req.username())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!encoder.matches(req.password(), user.getPassword()))
            throw new IllegalArgumentException("Invalid credentials");

        return jwt.generateToken(user.getUsername());
    }

    public UserDto me(String username) {
        User user = repo.findByUsername(username).orElseThrow();
        return new UserDto(user.getId(), user.getUsername(), user.getEmail());
    }
}
