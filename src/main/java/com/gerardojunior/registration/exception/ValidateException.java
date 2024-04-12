package com.gerardojunior.registration.exception;

import lombok.Getter;

@Getter
public class ValidateException extends RuntimeException {

    private final String code;
    private final transient Object body;

    public ValidateException(String code, String message) {
        super(message);
        this.code = code;
        this.body = null;
    }

    public ValidateException(String code, String message, Object body) {
        super(message);
        this.code = code;
        this.body = body;
    }
}
