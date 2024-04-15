package com.gerardojunior.registration.annotation.document;

import org.hibernate.validator.internal.util.annotation.AnnotationDescriptor;
import org.hibernate.validator.internal.util.annotation.AnnotationFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CPFValidatorTest {

    private final CPFValidator cpfValidator;

    public CPFValidatorTest() {
        cpfValidator = new CPFValidator();
    }

    @Test
    void testIsValidCPF() {
        cpfValidator.initialize(createAnnotation());
        boolean valid = cpfValidator.isValid("12345678909", null);
        assertTrue(valid);
    }

    @Test
    void testIsInvalidCPF() {
        cpfValidator.initialize(createAnnotation());
        boolean valid = cpfValidator.isValid("12345678901", null);
        assertFalse(valid);
    }

    @Test
    void testIsNullCPF() {
        cpfValidator.initialize(createAnnotation());
        boolean valid = cpfValidator.isValid(null, null);
        assertFalse(valid);
    }

    @Test
    void testIsInvalidCPFFormat() {
        cpfValidator.initialize(createAnnotation());
        boolean valid = cpfValidator.isValid("1234567890a", null);
        assertFalse(valid);
    }

    private CPF createAnnotation() {
        AnnotationDescriptor.Builder<CPF> descriptor = new AnnotationDescriptor.Builder<>(CPF.class);
        return AnnotationFactory.create(descriptor.build());
    }

}