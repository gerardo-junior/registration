package com.gerardojunior.registration.annotation.date;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class DateValidator implements ConstraintValidator<Date, Object> {

    private String regex;

    private Integer minimumYearsAgo;

    @Override
    public void initialize(Date date) {
        ConstraintValidator.super.initialize(date);
        this.regex = date.regex();
        this.minimumYearsAgo = Integer.valueOf(date.minimumYearsAgo());
    }

    @Override
    public boolean isValid(Object input, ConstraintValidatorContext context) {
        if (Objects.isNull(input) || !(input instanceof String value)) {
            return false;
        }

        if (!value.matches(this.regex)) {
            return false;
        }

        LocalDate date;

        try {
            date = LocalDate.parse(value);
        } catch (Exception e) {
            return false;
        }

        return this.minimumYearsAgo <= 0 || this.minimumYearsAgo <= ChronoUnit.YEARS.between(date, LocalDate.now());
    }

}