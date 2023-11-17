package com.gerardojunior.registration.dto;

import com.gerardojunior.registration.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{4,}$", message = "password must be min 4 length containing atleast 1 uppercase, 1 lowercase, 1 special character and 1 digit")
    private String password;

    private String address;

    private String mobileNumber;

    @NotNull
    private Gender gender;

}
