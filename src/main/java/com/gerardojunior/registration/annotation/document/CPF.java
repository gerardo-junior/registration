package com.gerardojunior.registration.annotation.document;

import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = CPFValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CPF {

    String regex() default "^(?!([\\d])\\1{10})[\\d]{11}$";

    String message() default "the field must contain a valid brazilian document";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

