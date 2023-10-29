package com.gerardojunior.registration.exception;

import lombok.Getter;

@Getter
public class ValidateException extends RuntimeException {

    private String code;
    private Object body;

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(String code, String message) {
        super(message);
        this.code = code;
    }

    public ValidateException(String code, String message, Object body) {
        super(message);
        this.code = code;
        this.body = body;
    }
}
