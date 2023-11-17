package com.gerardojunior.registration.dto;
import com.gerardojunior.registration.dto.AuthenticationRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthenticationRequestTest {

    @Test
    void testGetters() {
        // Arrange
        AuthenticationRequest request = AuthenticationRequest.builder()
                .email("john.doe@example.com")
                .password("Passw0rd!")
                .build();

        // Act
        String email = request.getEmail();
        String password = request.getPassword();

        // Assert
        assertEquals("john.doe@example.com", email);
        assertEquals("Passw0rd!", password);
    }
}
