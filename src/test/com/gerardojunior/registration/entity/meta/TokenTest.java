package com.gerardojunior.registration.entity.meta;

import com.gerardojunior.registration.enums.TokenType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TokenTest {

    @Test
    void testTokenEntity() {
        // Mocking
        User user = new User();
        Token token = Token.builder()
                .id(1)
                .token("testToken")
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .user(user)
                .build();

        // Verification
        assertEquals(1, token.getId());
        assertEquals("testToken", token.getToken());
        assertEquals(TokenType.BEARER, token.getTokenType());
        assertFalse(token.isRevoked());
        assertFalse(token.isExpired());
        assertEquals(user, token.getUser());

        // Test Setter
        token.setToken("newTestToken");
        assertEquals("newTestToken", token.getToken());

        token.setTokenType(TokenType.JWT);
        assertEquals(TokenType.JWT, token.getTokenType());

        token.setRevoked(true);
        assertTrue(token.isRevoked());

        token.setExpired(true);
        assertTrue(token.isExpired());

        User newUser = new User();
        token.setUser(newUser);
        assertEquals(newUser, token.getUser());
    }
}