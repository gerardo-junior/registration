package com.gerardojunior.registration.services.impl;

import com.gerardojunior.registration.config.JwtService;
import com.gerardojunior.registration.dto.AuthenticationRequest;
import com.gerardojunior.registration.dto.AuthenticationResponse;
import com.gerardojunior.registration.entity.meta.Token;
import com.gerardojunior.registration.entity.meta.User;
import com.gerardojunior.registration.helpers.RegisterUserRequestHelper;
import com.gerardojunior.registration.helpers.TokenHelper;
import com.gerardojunior.registration.repositories.TokenRepository;
import com.gerardojunior.registration.repositories.UserRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    void testAuthenticate() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("test@example.com", "password");
        User user =  RegisterUserRequestHelper.createValidUser();
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(tokenRepository.findAllByUser_IdAndExpiredFalseAndRevokedFalse(user.getId())).thenReturn(Collections.emptyList());
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(tokenRepository.save(any(Token.class))).thenReturn(TokenHelper.getToken());

        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);

        assertNotNull(authenticationResponse);
    }

    @Test
    void testAuthenticateRevokingUserTokens() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("test@example.com", "password");
        User user =  RegisterUserRequestHelper.createValidUser();
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(tokenRepository.findAllByUser_IdAndExpiredFalseAndRevokedFalse(user.getId())).thenReturn(List.of(TokenHelper.getToken()));
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(tokenRepository.save(any(Token.class))).thenReturn(TokenHelper.getToken());
        when(tokenRepository.saveAll(any())).thenReturn(null);

        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);

        assertNotNull(authenticationResponse);
    }

    @Test
    void testRefreshToken() throws IOException {
        String refreshToken = "dummy_refresh_token";
        User user =  RegisterUserRequestHelper.createValidUser();
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

        when(httpServletRequest.getHeader("Authorization")).thenReturn("Bearer " + refreshToken);
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(jwtService.extractUsername(any())).thenReturn("test@example.com");

        authenticationService.refreshToken(httpServletRequest, httpServletResponse);
        verify(userRepository).findByEmail(any());
    }

    @Test
    void testRefreshTokenWithValidToken() throws IOException {
        String refreshToken = "dummy_refresh_token";
        User user =  RegisterUserRequestHelper.createValidUser();
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        ServletOutputStream servletOutputStream = mock(ServletOutputStream.class);

        when(httpServletRequest.getHeader("Authorization")).thenReturn("Bearer " + refreshToken);
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(jwtService.extractUsername(any())).thenReturn("test@example.com");
        when(jwtService.isTokenValid(anyString(), any(User.class))).thenReturn(true);
        when(httpServletResponse.getOutputStream()).thenReturn(servletOutputStream);

        authenticationService.refreshToken(httpServletRequest, httpServletResponse);
        verify(userRepository).findByEmail(any());
    }

}
