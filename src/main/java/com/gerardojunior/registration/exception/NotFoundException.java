package com.gerardojunior.registration.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private final String code;
    private final transient Object body;

    public NotFoundException(String message, String code) {
        super(message);
        this.code = code;
        this.body = null;
    }
}
