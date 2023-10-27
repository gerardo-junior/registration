package com.gerardojunior.registration.dto;

import com.gerardojunior.registration.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.gerardojunior.registration.commons.annotation.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;

    @NotNull
    @Document(message = "the document field must contain a valid cpf")
    private String document;

    @NotBlank
    @Email(message = "the email field must contain a valid email")
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{4,}$", message = "password must be min 4 length containing atleast 1 uppercase, 1 lowercase, 1 special character and 1 digit")
    private String password;

    private String address;

    private String mobileNumber;

    @NotNull
    private Gender gender;

}
