package com.gerardojunior.registration.annotation.date;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class DateValidator implements ConstraintValidator<Date, Object> {

    private String regex;

    private Integer minYearsAgo;

    private Integer maxYearsAgo;

    @Override
    public void initialize(Date date) {

        ConstraintValidator.super.initialize(date);
        this.regex = date.regex();
        this.minYearsAgo = Integer.valueOf(date.minYearsAgo());
        this.maxYearsAgo = Integer.valueOf(date.maxYearsAgo());

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

        if (0 < this.minYearsAgo || 0 < this.maxYearsAgo) {
            long diffOfNow = ChronoUnit.YEARS.between(date, LocalDate.now());

            return this.minYearsAgo >= diffOfNow || this.maxYearsAgo < diffOfNow;
        }

        return true;
    }

}