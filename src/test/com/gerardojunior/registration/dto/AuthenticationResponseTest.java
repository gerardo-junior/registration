package com.gerardojunior.registration.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthenticationResponseTest {

    @Test
    void testGetters() {
        // Arrange
        AuthenticationResponse response = AuthenticationResponse.builder()
                .accessToken("access_token_value")
                .refreshToken("refresh_token_value")
                .build();

        // Act
        String accessToken = response.getAccessToken();
        String refreshToken = response.getRefreshToken();

        // Assert
        assertEquals("access_token_value", accessToken);
        assertEquals("refresh_token_value", refreshToken);
    }

    @Test
    void testJsonPropertyNames() {
        // Arrange
        AuthenticationResponse response = AuthenticationResponse.builder()
                .accessToken("access_token_value")
                .refreshToken("refresh_token_value")
                .build();

        // Act
        String json = "{\"access_token\":\"access_token_value\",\"refresh_token\":\"refresh_token_value\"}";

        // Assert
        assertEquals(json, response.toString());
    }
}

