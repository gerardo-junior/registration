package com.gerardojunior.registration.dto;

import com.gerardojunior.registration.annotation.date.Date;
import com.gerardojunior.registration.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.gerardojunior.registration.annotation.document.CPF;
import org.apache.commons.lang3.EnumUtils;

import java.time.LocalDate;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequest {

    @NotBlank
    @CPF(message = "the document field must contain a valid CPF")
    private String document;

    @NotBlank
    private String firstname;

    private String lastname;

    @NotBlank
    @Date(minYearsAgo = "18",
          message = "must contain a valid date at least 18 years ago in the format YYYY-MM-DD")
    private String dateOfBirth;

    @NotBlank
    @Email(message = "the email field must contain a valid email")
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{4,}$",
             message = "password must be min 4 length containing atleast 1 uppercase, 1 lowercase, 1 special character and 1 digit")
    private String password;

    private String address;

    private String mobileNumber;

    private String gender;

    public LocalDate getDateOfBirth() {
        return LocalDate.parse(this.dateOfBirth);
    }

    public Gender getGender() {
        return Objects.nonNull(this.gender) && EnumUtils.isValidEnum(Gender.class, this.gender)  ? Gender.valueOf(this.gender) : Gender.UNKNOWN;
    }

}
