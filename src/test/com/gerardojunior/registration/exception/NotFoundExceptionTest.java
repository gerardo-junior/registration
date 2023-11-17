package com.gerardojunior.registration.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private String code;
    private Object body;

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String code, String message) {
        super(message);
        this.code = code;
    }

    public NotFoundException(String code, String message, Object body) {
        super(message);
        this.code = code;
        this.body = body;
    }
}
