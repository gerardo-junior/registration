package com.gerardojunior.registration.services;

import com.gerardojunior.registration.dto.AuthenticationRequest;
import com.gerardojunior.registration.dto.AuthenticationResponse;
import com.gerardojunior.registration.entity.meta.Token;
import com.gerardojunior.registration.entity.meta.User;
import com.gerardojunior.registration.exception.NotFoundException;
import com.gerardojunior.registration.repositories.TokenRepository;
import com.gerardojunior.registration.repositories.UserRepository;
import com.gerardojunior.registration.config.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authenticate_ValidCredentials_ReturnsAuthenticationResponse() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest("test@example.com", "password");
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("hashedPassword");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userRepository.findByEmail("test@example.com")).thenReturn(java.util.Optional.of(user));
        when(jwtService.generateToken(any(User.class))).thenReturn("fakeAccessToken");
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn("fakeRefreshToken");
        when(tokenRepository.findAllValidTokenByUser(anyInt())).thenReturn(Collections.emptyList());

        // Act
        AuthenticationResponse response = authenticationService.authenticate(request);

        // Assert
        assertNotNull(response);
        assertEquals("fakeAccessToken", response.getAccessToken());
        assertEquals("fakeRefreshToken", response.getRefreshToken());
        verify(tokenRepository, times(1)).save(any(Token.class));
    }

    @Test
    void authenticate_InvalidCredentials_ThrowsUsernameNotFoundException() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest("nonexistent@example.com", "password");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new UsernameNotFoundException("User not found"));

        // Act & Assert
        assertThrows(NotFoundException.class, () -> authenticationService.authenticate(request));
        verify(tokenRepository, never()).save(any(Token.class));
    }

    @Test
    void refreshToken_ValidRefreshToken_ReturnsAuthenticationResponse() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getHeader(anyString())).thenReturn("Bearer fakeRefreshToken");
        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(java.util.Optional.of(user));
        when(jwtService.extractUsername("fakeRefreshToken")).thenReturn("test@example.com");
        when(jwtService.isTokenValid("fakeRefreshToken", user)).thenReturn(true);
        when(jwtService.generateToken(any(User.class))).thenReturn("newFakeAccessToken");
        when(tokenRepository.findAllValidTokenByUser(anyInt())).thenReturn(List.of(new Token()));

        // Act
        authenticationService.refreshToken(request, response);

        // Assert
        verify(response, times(1)).getOutputStream();
    }

    @Test
    void refreshToken_InvalidRefreshToken_DoesNotWriteResponse() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getHeader(anyString())).thenReturn("Bearer invalidToken");

        // Act
        authenticationService.refreshToken(request, response);

        // Assert
        verify(response, never()).getOutputStream();
    }
}
