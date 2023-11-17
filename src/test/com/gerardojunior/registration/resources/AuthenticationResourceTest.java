package com.gerardojunior.registration.resources;
import com.gerardojunior.registration.dto.AuthenticationRequest;
import com.gerardojunior.registration.dto.AuthenticationResponse;
import com.gerardojunior.registration.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthenticationResourceTest {

    private static final String SAMPLE_TOKEN = "sampleToken";

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationResource authenticationResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authenticate() {
        // Mocking
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(SAMPLE_TOKEN);
        when(authenticationService.authenticate(authenticationRequest)).thenReturn(authenticationResponse);

        // Test
        ResponseEntity<AuthenticationResponse> responseEntity = authenticationResource.authenticate(authenticationRequest);

        // Verification
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("AuthSuccessfully", responseEntity.getBody().getMeta().getCode());
        assertEquals("Authentication completed successfully", responseEntity.getBody().getMeta().getMessage());
        assertEquals(authenticationResponse, responseEntity.getBody().getData());

        // Verify that the service method was called
        verify(authenticationService, times(1)).authenticate(authenticationRequest);
    }

    @Test
    void refreshToken() throws IOException {
        // Mocking
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // Test
        authenticationResource.refreshToken(request, response);

        // Verification
        // Verify that the service method was called
        verify(authenticationService, times(1)).refreshToken(request, response);
    }
}