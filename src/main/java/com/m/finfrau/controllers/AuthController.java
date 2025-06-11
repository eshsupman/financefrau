package com.m.finfrau.controllers;

import com.m.finfrau.requests.RegisterRequest;
import com.m.finfrau.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        boolean success = userService.registerUser(request.getUsername(), request.getPassword());
        if (success) {
            return ResponseEntity.ok("Пользователь зарегистрирован");
        } else {
            return ResponseEntity.badRequest().body("Имя пользователя занято");
        }
    }
}
