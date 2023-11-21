package com.gerardojunior.registration.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ValidateExceptionTest {

    @Test
    public void createValidateExceptionWithMessage() {
        // Arrange
        String errorMessage = "Validation error";

        // Act
        ValidateException validateException = new ValidateException(errorMessage);

        // Assert
        assertEquals(errorMessage, validateException.getMessage());
        assertNull(validateException.getCode());
        assertNull(validateException.getBody());
    }

    @Test
    public void createValidateExceptionWithCodeAndMessage() {
        // Arrange
        String errorCode = "VALIDATION_ERROR";
        String errorMessage = "Validation error";

        // Act
        ValidateException validateException = new ValidateException(errorCode, errorMessage);

        // Assert
        assertEquals(errorMessage, validateException.getMessage());
        assertEquals(errorCode, validateException.getCode());
        assertNull(validateException.getBody());
    }

    @Test
    public void createValidateExceptionWithCodeMessageAndBody() {
        // Arrange
        String errorCode = "VALIDATION_ERROR";
        String errorMessage = "Validation error";
        Object errorBody = new Object(); // Replace with your specific object

        // Act
        ValidateException validateException = new ValidateException(errorCode, errorMessage, errorBody);

        // Assert
        assertEquals(errorMessage, validateException.getMessage());
        assertEquals(errorCode, validateException.getCode());
        assertEquals(errorBody, validateException.getBody());
    }
}