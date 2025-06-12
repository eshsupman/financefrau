package com.m.finfrau;

import com.m.finfrau.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
    }

    @Test
    void testGenerateAndExtractToken() {
        String username = "testuser";
        String token = jwtService.generateToken(username);

        assertNotNull(token);

        String extractedUsername = jwtService.extractUsername(token);
        assertEquals(username, extractedUsername);
    }

    @Test
    void testValidateToken_Valid() {
        String username = "testuser";
        String token = jwtService.generateToken(username);

        UserDetails userDetails = User.withUsername(username).password("password").roles("USER").build();

        boolean isValid = jwtService.validateToken(token, userDetails);
        assertTrue(isValid);
    }

    @Test
    void testValidateToken_Invalid() {
        String invalidToken = "some.invalid.token";

        UserDetails userDetails = User.withUsername("testuser").password("password").roles("USER").build();

        boolean isValid = jwtService.validateToken(invalidToken, userDetails);
        assertFalse(isValid);
    }
}
