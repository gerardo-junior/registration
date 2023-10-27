package com.gerardojunior.registration.commons.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class DocumentValidator implements ConstraintValidator<Document, String> {

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (Objects.isNull(cpf) || !cpf.matches("\\d{11}") || cpf.matches("^(.)\\1*$")) {
            return false;
        }

        int d1, d2, digit1, digit2, mod, digitCPF;
        String nVerifyingDigit;

        d1 = d2 = digit1 = digit2 = mod = 0;

        for (int i = 1; i < cpf.length() - 1; i++) {
            digitCPF = Integer.valueOf(cpf.substring(i - 1, i)).intValue();

            d1 = d1 + (11 - i) * digitCPF;
            d2 = d2 + (12 - i) * digitCPF;
        }

        mod = (d1 % 11);
        digit1 = mod < 2 ? 0 : 11 - mod;
        d2 += 2 * digit1;
        mod = (d2 % 11);
        digit2 = mod < 2 ? 0 : 11 - mod;

        String nDigVerific = cpf.substring(cpf.length() - 2);

        nVerifyingDigit = String.valueOf(digit1) + digit2;

        return nDigVerific.equals(nVerifyingDigit);
    }

}