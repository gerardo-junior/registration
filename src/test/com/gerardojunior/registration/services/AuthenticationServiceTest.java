package com.gerardojunior.registration.services;

import com.gerardojunior.registration.dto.AuthenticationRequest;
import com.gerardojunior.registration.dto.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
