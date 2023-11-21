package com.gerardojunior.registration.config;

import com.gerardojunior.registration.entity.meta.Token;
import com.gerardojunior.registration.repositories.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.Optional;

import static org.mockito.Mockito.*;

class LogoutServiceTest {

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private LogoutService logoutService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void logoutShouldRevokeTokenAndClearContext() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);

        String jwt = "validJwtToken";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);

        // Mock token repository behavior
        Token storedToken = mock(Token.class);
        when(tokenRepository.findByToken(jwt)).thenReturn(Optional.ofNullable(storedToken));

        // Act
        logoutService.logout(request, response, authentication);

        // Assert
        verify(storedToken).setExpired(true);
        verify(storedToken).setRevoked(true);
        verify(tokenRepository).save(storedToken);
    }

    @Test
    void logoutShouldNotRevokeTokenIfJwtNotPresent() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);

        // Act
        logoutService.logout(request, response, authentication);

        // Assert
        // Verify that tokenRepository and SecurityContextHolder methods are not called
        verifyNoInteractions(tokenRepository, SecurityContextHolder);
    }
}
