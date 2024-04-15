package com.gerardojunior.registration.annotation.date;

import org.hibernate.validator.internal.util.annotation.AnnotationDescriptor;
import org.hibernate.validator.internal.util.annotation.AnnotationFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DateValidatorTest {

    private final DateValidator dateValidator;

    public DateValidatorTest() {
        dateValidator = new DateValidator();
    }

    @Test
    void testIsValidDate() {
        dateValidator.initialize(createAnnotation("0", "0"));
        boolean valid = dateValidator.isValid("2021-01-01", null);
        assertTrue(valid);
    }

    @ParameterizedTest
    @ValueSource(strings = {"2021-01-32", "2021-13-01", "2021/01/01"})
    void testIsInvalidDate() {
        dateValidator.initialize(createAnnotation("0", "0"));
        boolean valid = dateValidator.isValid("2021-01-32", null);
        assertFalse(valid);
    }

    @Test
    void testIsNullDate() {
        dateValidator.initialize(createAnnotation("0", "0"));
        boolean valid = dateValidator.isValid(null, null);
        assertFalse(valid);
    }

    @ParameterizedTest
    @CsvSource({ "1,0,true", "0,1,true", "18,0,false", "0,18,true", "18,1,false", "18,18,true" })
    void testIsValidDateWithMinAndMaxYearsAgo(String minYearsAgo, String maxYearsAgo, String expected) {
        dateValidator.initialize(createAnnotation(minYearsAgo, maxYearsAgo));
        boolean valid = dateValidator.isValid("2021-01-01", null);
        assertEquals(Boolean.parseBoolean(expected), valid);
    }

    private Date createAnnotation(String minYearsAgo, String maxYearsAgo) {
        AnnotationDescriptor.Builder<Date> descriptor = new AnnotationDescriptor.Builder<>(Date.class);
        descriptor.setAttribute("minYearsAgo", minYearsAgo);
        descriptor.setAttribute("maxYearsAgo", maxYearsAgo);
        return AnnotationFactory.create(descriptor.build());
    }

}