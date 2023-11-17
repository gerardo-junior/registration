package com.gerardojunior.registration.dto;

import com.gerardojunior.registration.enums.Gender;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UpdateUserRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidUpdateUserRequest() {
        // Arrange
        UpdateUserRequest request = UpdateUserRequest.builder()
                .firstname("John")
                .lastname("Doe")
                .password("Passw0rd!")
                .address("123 Main St")
                .mobileNumber("1234567890")
                .gender(Gender.MALE)
                .build();

        // Act
        Set<ConstraintViolation<UpdateUserRequest>> violations = validator.validate(request);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidUpdateUserRequest() {
        // Arrange
        UpdateUserRequest request = UpdateUserRequest.builder()
                .firstname("")
                .lastname("Doe")
                .password("password")  // Invalid password
                .address("123 Main St")
                .mobileNumber("1234567890")
                .gender(Gender.MALE)
                .build();

        // Act
        Set<ConstraintViolation<UpdateUserRequest>> violations = validator.validate(request);

        // Assert
        assertEquals(2, violations.size()); // Two fields are invalid: firstname, password
    }

    @Test
    void testNullGender() {
        // Arrange
        UpdateUserRequest request = UpdateUserRequest.builder()
                .firstname("John")
                .lastname("Doe")
                .password("Passw0rd!")
                .address("123 Main St")
                .mobileNumber("1234567890")
                .gender(null)
                .build();

        // Act
        Set<ConstraintViolation<UpdateUserRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
    }
}

