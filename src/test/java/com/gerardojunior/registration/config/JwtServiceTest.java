package com.gerardojunior.registration.config;

import com.gerardojunior.registration.entity.meta.User;
import com.gerardojunior.registration.helpers.RegisterUserRequestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private final JwtService jwtService;

    public JwtServiceTest() {
        String secret = "0000000000000000000000000000000000000000000000000000000000000000";
        jwtService = new JwtService(secret, 60 * 60 * 1000, 60 * 60 * 1000);
    }

    @Test
    void extractUsername() {
        String token = jwtService.generateToken(RegisterUserRequestHelper.createValidUser());

        assertEquals("john.doe@example.com", jwtService.extractUsername(token));
    }

    @Test
    void testGenerateRefreshToken() {
        String token = jwtService.generateRefreshToken(RegisterUserRequestHelper.createValidUser());

        assertEquals("john.doe@example.com", jwtService.extractUsername(token));
    }

    @Test
    void testIsTokenValid() {
        User user = RegisterUserRequestHelper.createValidUser();
        String token = jwtService.generateToken(user);
        boolean tokenValid = jwtService.isTokenValid(token, user);

        assertTrue(tokenValid);
    }

}