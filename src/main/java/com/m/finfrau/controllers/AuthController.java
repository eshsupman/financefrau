package com.m.finfrau.controllers;

import com.m.finfrau.repositories.UserRepository;
import com.m.finfrau.service.JwtService;
import model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        userRepository.saveUser(user.getUsername(), encodedPassword);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());

        if (userOptional.isPresent()) {
            User dbUser = userOptional.get();
            if (passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
                String token = jwtService.generateToken(dbUser.getUsername());
                return ResponseEntity.ok(Collections.singletonMap("token", token));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}