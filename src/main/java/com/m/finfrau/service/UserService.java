package com.m.finfrau.service;

import com.m.finfrau.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public boolean registerUser(String username, String rawPassword) {
        if (userRepository.userExists(username)) {
            return false;
        }
        String hashedPassword = passwordEncoder.encode(rawPassword);
        userRepository.saveUser(username, hashedPassword);
        return true;
    }
}

