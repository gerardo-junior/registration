package com.gerardojunior.registration.annotation.document;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class CPFValidatorTest {

    @Test
    void testValidCPF() {
        CPFValidator validator = new CPFValidator();

        assertTrue(validator.isValid("12345678909", mock(ConstraintValidatorContext.class))); // Replace with an actual valid CPF
    }

    @Test
    void testInvalidCPF() {
        CPFValidator validator = new CPFValidator();

        assertFalse(validator.isValid("00000000000", mock(ConstraintValidatorContext.class))); // Replace with an actual invalid CPF
    }

    @Test
    void testInvalidFormat() {
        CPFValidator validator = new CPFValidator();

        assertFalse(validator.isValid("123.456.789-09", mock(ConstraintValidatorContext.class))); // Replace with an invalid format CPF
    }

    @Test
    void testEmptyCPF() {
        CPFValidator validator = new CPFValidator();

        assertFalse(validator.isValid("", mock(ConstraintValidatorContext.class))); // Empty CPF should be invalid
    }

    @Test
    void testNullCPF() {
        CPFValidator validator = new CPFValidator();

        assertFalse(validator.isValid(null, mock(ConstraintValidatorContext.class))); // Null CPF should be invalid
    }
}
}