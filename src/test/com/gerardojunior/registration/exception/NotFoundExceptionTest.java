package com.gerardojunior.registration.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class NotFoundExceptionTest {

    @Test
    public void createNotFoundExceptionWithMessage() {
        // Arrange
        String errorMessage = "Resource not found";

        // Act
        NotFoundException notFoundException = new NotFoundException(errorMessage);

        // Assert
        assertEquals(errorMessage, notFoundException.getMessage());
        assertEquals("404 NOT_FOUND", notFoundException.getCode());
        assertNull(notFoundException.getBody());
    }

    @Test
    public void createNotFoundExceptionWithCodeAndMessage() {
        // Arrange
        String errorCode = "RESOURCE_NOT_FOUND";
        String errorMessage = "Resource not found";

        // Act
        NotFoundException notFoundException = new NotFoundException(errorCode, errorMessage);

        // Assert
        assertEquals(errorMessage, notFoundException.getMessage());
        assertEquals(errorCode, notFoundException.getCode());
        assertNull(notFoundException.getBody());
    }

    @Test
    public void createNotFoundExceptionWithCodeMessageAndBody() {
        // Arrange
        String errorCode = "RESOURCE_NOT_FOUND";
        String errorMessage = "Resource not found";
        Object errorBody = new Object(); // Replace with your specific object

        // Act
        NotFoundException notFoundException = new NotFoundException(errorCode, errorMessage, errorBody);

        // Assert
        assertEquals(errorMessage, notFoundException.getMessage());
        assertEquals(errorCode, notFoundException.getCode());
        assertEquals(errorBody, notFoundException.getBody());
    }
}