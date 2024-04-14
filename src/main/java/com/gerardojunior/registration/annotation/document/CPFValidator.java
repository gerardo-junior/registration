package com.gerardojunior.registration.annotation.document;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class CPFValidator implements ConstraintValidator<CPF, String> {

    private String regex;

    @Override
    public void initialize(CPF cpf) {
        ConstraintValidator.super.initialize(cpf);
        this.regex = cpf.regex();

    }

    @Override
    public boolean isValid(String input, ConstraintValidatorContext context) {
        if (Objects.isNull(input) || !input.matches(this.regex)) {
            return false;
        }

        int sumFirstCheckDigit = 0;
        int sumSecondCheckDigit = 0;
        int digit1;
        int digit2;
        int mod;
        int digitCPF;

        for (int i = 1; i < input.length() - 1; i++) {
            digitCPF = Integer.parseInt(input.substring(i - 1, i));

            sumFirstCheckDigit += (11 - i) * digitCPF;
            sumSecondCheckDigit += (12 - i) * digitCPF;
        }

        mod = (sumFirstCheckDigit % 11);
        digit1 = mod < 2 ? 0 : 11 - mod;
        sumSecondCheckDigit += 2 * digit1;
        mod = (sumSecondCheckDigit % 11);
        digit2 = mod < 2 ? 0 : 11 - mod;

        return input.substring(input.length() - 2).equals(String.valueOf(digit1) + digit2);
    }

}