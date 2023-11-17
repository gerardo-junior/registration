package com.gerardojunior.registration.annotation.date;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class DateValidatorTest {

    @Test
    void testValidDate() {
        DateValidator validator = new DateValidator();
        Date dateAnnotation = mock(Date.class);

        assertTrue(validator.isValid("2021-01-01", mock(ConstraintValidatorContext.class))); // Replace with an actual valid date
    }

    @Test
    void testInvalidDate() {
        DateValidator validator = new DateValidator();
        Date dateAnnotation = mock(Date.class);

        assertFalse(validator.isValid("invalid_date", mock(ConstraintValidatorContext.class)));
    }

    @Test
    void testDateWithinRange() {
        DateValidator validator = new DateValidator();
        Date dateAnnotation = mock(Date.class);
        validator.initialize(dateAnnotation);
        validator.minYearsAgo = 1;
        validator.maxYearsAgo = 5;

        assertTrue(validator.isValid("2020-01-01", mock(ConstraintValidatorContext.class))); // Replace with an actual date within the range
    }

    @Test
    void testDateOutOfRange() {
        DateValidator validator = new DateValidator();
        Date dateAnnotation = mock(Date.class);
        validator.initialize(dateAnnotation);
        validator.minYearsAgo = 1;
        validator.maxYearsAgo = 5;

        assertFalse(validator.isValid("2010-01-01", mock(ConstraintValidatorContext.class))); // Replace with an actual date outside the range
    }

    @Test
    void testDateWithInvalidFormat() {
        DateValidator validator = new DateValidator();
        Date dateAnnotation = mock(Date.class);

        assertFalse(validator.isValid("20210101", mock(ConstraintValidatorContext.class))); // Replace with an actual date with an invalid format
    }
}