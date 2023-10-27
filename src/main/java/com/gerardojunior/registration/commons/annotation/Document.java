package com.gerardojunior.registration.commons.annotation;

import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = DocumentValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Document {
    String message() default "the document field must contain a valid cpf";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

