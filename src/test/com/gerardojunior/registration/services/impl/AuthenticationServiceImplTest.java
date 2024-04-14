package com.gerardojunior.registration.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerardojunior.registration.config.JwtService;
import com.gerardojunior.registration.dto.AuthenticationRequest;
import com.gerardojunior.registration.dto.AuthenticationResponse;
import com.gerardojunior.registration.entity.meta.Token;
import com.gerardojunior.registration.entity.meta.User;
import com.gerardojunior.registration.enums.TokenType;
import com.gerardojunior.registration.exception.NotFoundException;
import com.gerardojunior.registration.helpers.RegisterUserRequestHelper;
import com.gerardojunior.registration.repositories.TokenRepository;
import com.gerardojunior.registration.repositories.UserRepository;
import com.gerardojunior.registration.services.AuthenticationService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        when(tokenRepository.save(any(Token.class))).thenReturn(new Token());

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
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ServletOutputStream servletOutputStream = new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                byteArrayOutputStream.write(b);
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {

            }
        };

        authenticationService.refreshToken(httpServletRequest, httpServletResponse);

        verify(userRepository).findByEmail(any());
    }
}
