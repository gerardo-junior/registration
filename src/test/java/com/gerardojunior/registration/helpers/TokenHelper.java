package com.gerardojunior.registration.helpers;

import com.gerardojunior.registration.entity.meta.Token;
import com.gerardojunior.registration.enums.TokenType;

public class TokenHelper {

    public static Token getToken() {
        return Token.builder()
                .id(1)
                .value("valid token")
                .user(RegisterUserRequestHelper.createValidUser())
                .type(TokenType.BEARER)
                .expired(false)
                .revoked(false)
            .build();
    }

}
