package com.gerardojunior.registration.advisers;

import com.gerardojunior.registration.exception.NotFoundException;
import com.gerardojunior.registration.exception.ValidateException;
import com.gerardojunior.registration.util.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
@CrossOrigin
public class AppWideExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public StandardResponse handleException(Exception e) {
        return new StandardResponse("InternalError", e.getMessage(), null);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public StandardResponse handleNotFoundException(NotFoundException e) {
        return new StandardResponse(Objects.isNull(e.getCode()) ? "NotFound" : e.getCode(), Objects.isNull(e.getMessage()) ? "NotFound" : e.getMessage(), e.getBody());
    }

    @ExceptionHandler({ValidateException.class, HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public StandardResponse handleValidationException(ValidateException e) {
        return new StandardResponse(Objects.isNull(e.getCode()) ? "400" : e.getCode(), Objects.isNull(e.getMessage()) ? "Error" : e.getMessage(), e.getBody());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public StandardResponse handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new StandardResponse("ExceptionBodyValidation", "Validate body exception", errors);
    }
}
