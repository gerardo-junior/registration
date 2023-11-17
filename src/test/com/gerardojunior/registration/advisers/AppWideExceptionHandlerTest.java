package com.gerardojunior.registration.advisers;

import com.gerardojunior.registration.exception.NotFoundException;
import com.gerardojunior.registration.exception.ValidateException;
import com.gerardojunior.registration.util.StandardResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.HashMap;

class AppWideExceptionHandlerTest {

    @Test
    void handleException() {
        AppWideExceptionHandler handler = new AppWideExceptionHandler();
        Exception exception = new Exception("Test exception");

        ResponseEntity<StandardResponse> responseEntity = handler.handleException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("InternalError", responseEntity.getBody().getCode());
        assertEquals("Test exception", responseEntity.getBody().getMessage());
        assertEquals(null, responseEntity.getBody().getBody());
    }

    @Test
    void handleNotFoundException() {
        AppWideExceptionHandler handler = new AppWideExceptionHandler();
        NotFoundException exception = new NotFoundException("Test not found exception", "NotFound", null);

        ResponseEntity<StandardResponse> responseEntity = handler.handleNotFoundException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("NotFound", responseEntity.getBody().getCode());
        assertEquals("Test not found exception", responseEntity.getBody().getMessage());
        assertEquals(null, responseEntity.getBody().getBody());
    }

    @Test
    void handleValidationException() {
        AppWideExceptionHandler handler = new AppWideExceptionHandler();
        ValidateException exception = new ValidateException("Test validation exception", "ValidationCode", null);

        ResponseEntity<StandardResponse> responseEntity = handler.handleValidationException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("ValidationCode", responseEntity.getBody().getCode());
        assertEquals("Test validation exception", responseEntity.getBody().getMessage());
        assertEquals(null, responseEntity.getBody().getBody());
    }

    @Test
    void handleValidationExceptions() {
        AppWideExceptionHandler handler = new AppWideExceptionHandler();
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        Mockito.when(exception.getBindingResult()).thenReturn(Mockito.mock(org.springframework.validation.BindingResult.class));
        Mockito.when(exception.getBindingResult().getAllErrors()).thenReturn(mock(java.util.List.class));

        ResponseEntity<StandardResponse> responseEntity = handler.handleValidationExceptions(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("ExceptionBodyValidation", responseEntity.getBody().getCode());
        assertEquals("Validate body exception", responseEntity.getBody().getMessage());
        assertEquals(new HashMap<>(), responseEntity.getBody().getBody());
    }

    @Test
    void handleHttpMessageNotReadableException() {
        AppWideExceptionHandler handler = new AppWideExceptionHandler();
        HttpMessageNotReadableException exception = new HttpMessageNotReadableException("Test message not readable exception");

        ResponseEntity<StandardResponse> responseEntity = handler.handleValidationException(new ValidateException("Test validation exception", "ValidationCode", null));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("400", responseEntity.getBody().getCode());
        assertEquals("Test validation exception", responseEntity.getBody().getMessage());
        assertEquals(null, responseEntity.getBody().getBody());
    }
}
