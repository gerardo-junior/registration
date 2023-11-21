package com.gerardojunior.registration.config;

import com.gerardojunior.registration.entity.meta.User;
import com.gerardojunior.registration.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    @Test
    void testGenerateToken() {
        // Arrange
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("testUser");
        when(user.getRole()).thenReturn(Role.USER);

        // Act
        String token = jwtService.generateToken(user);

        // Assert
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void testGenerateRefreshToken() {
        // Arrange
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("testUser");
        when(user.getRole()).thenReturn(Role.USER);

        // Act
        String refreshToken = jwtService.generateRefreshToken(user);

        // Assert
        assertNotNull(refreshToken);
        assertTrue(refreshToken.length() > 0);
    }

    @Test
    void testIsTokenValid() {
        // Arrange
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("testUser");
        when(user.getRole()).thenReturn(Role.USER);

        String token = jwtService.generateToken(user);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testUser");

        // Act
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void testIsTokenInvalid() {
        // Arrange
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("testUser");
        when(user.getRole()).thenReturn(Role.USER);

        String token = jwtService.generateToken(user);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("otherUser");

        // Act
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        // Assert
        assertFalse(isValid);
    }
}
