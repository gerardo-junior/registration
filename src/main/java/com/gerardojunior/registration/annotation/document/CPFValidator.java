package com.gerardojunior.registration.annotation.document;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class CPFValidator implements ConstraintValidator<CPF, String> {

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (Objects.isNull(cpf) || !cpf.matches("\\d{11}") || cpf.matches("^(.)\\1*$")) {
            return false;
        }

        int d1, d2, digit1, digit2, mod, digitCPF;
        d1 = d2 = 0;

        for (int i = 1; i < cpf.length() - 1; i++) {
            digitCPF = Integer.parseInt(cpf.substring(i - 1, i));

            d1 = d1 + (11 - i) * digitCPF;
            d2 = d2 + (12 - i) * digitCPF;
        }

        mod = (d1 % 11);
        digit1 = mod < 2 ? 0 : 11 - mod;
        d2 += 2 * digit1;
        mod = (d2 % 11);
        digit2 = mod < 2 ? 0 : 11 - mod;

        return cpf.substring(cpf.length() - 2).equals(String.valueOf(digit1) + digit2);
    }

}