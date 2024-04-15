package com.gerardojunior.registration.resources;

import com.gerardojunior.registration.dto.AuthenticationRequest;
import com.gerardojunior.registration.dto.AuthenticationResponse;
import com.gerardojunior.registration.services.AuthenticationService;
import com.gerardojunior.registration.util.StandardResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationResourceTest {

    @Mock
    private AuthenticationService authenticationService;
    @InjectMocks
    private AuthenticationResource authenticationResource;

    @Test
    void testAuthenticate() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("test@example.com", "password");
        AuthenticationResponse authenticationResponse = new AuthenticationResponse("access_token", "refresh_token");
        when(authenticationService.authenticate(any(AuthenticationRequest.class))).thenReturn(authenticationResponse);

        StandardResponse<AuthenticationResponse> response = authenticationResource.authenticate(authenticationRequest);
        assertEquals("AuthSuccessfully", response.getMeta().getCode());
        assertEquals("Authentication completed successfully", response.getMeta().getMessage());
        assertEquals("access_token", response.getData().getAccessToken());
        assertEquals("refresh_token", response.getData().getRefreshToken());
    }

    @Test
    void testRefreshToken() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        doNothing().when(authenticationService).refreshToken(any(HttpServletRequest.class), any(HttpServletResponse.class));

        authenticationResource.refreshToken(request, response);
        verify(authenticationService, times(1)).refreshToken(request, response);
    }

}
