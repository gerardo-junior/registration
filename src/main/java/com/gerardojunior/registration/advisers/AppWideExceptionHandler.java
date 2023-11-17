package com.gerardojunior.registration.advisers;

import com.gerardojunior.registration.exception.NotFoundException;
import com.gerardojunior.registration.exception.ValidateException;
import com.gerardojunior.registration.util.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
@CrossOrigin
public class AppWideExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        return new ResponseEntity(new StandardResponse("InternalError", e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException(NotFoundException e) {
        return new ResponseEntity(new StandardResponse(Objects.isNull(e.getCode()) ? "NotFound" : e.getCode(), Objects.isNull(e.getMessage()) ? "NotFound" : e.getMessage(), e.getBody()), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler({ValidateException.class})
    public ResponseEntity handleValidationException(ValidateException e) {
        return new ResponseEntity(new StandardResponse(Objects.isNull(e.getCode()) ? "400" : e.getCode(), Objects.isNull(e.getMessage()) ? "Error" : e.getMessage(), e.getBody()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity(new StandardResponse("ExceptionBodyValidation", "Validate body exception", errors), HttpStatus.BAD_REQUEST);
    }
}
