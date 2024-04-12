package com.gerardojunior.registration.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gerardojunior.registration.enums.Gender;
import com.gerardojunior.registration.enums.Role;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    private String id;

    private String document;

    private String firstname;

    private String lastname;

    private LocalDate dateOfBirth;

    private String address;

    private String mobileNumber;

    private Gender gender;

    private String email;

    private Role role;

    @JsonProperty("gender")
    public String getGender() {
        return this.gender.toString();
    }

    @JsonProperty("role")
    public String getRole() {
        return this.role.toString();
    }

    @JsonProperty("dateOfBirth")
    public String getDateOfBirth() {
        return this.dateOfBirth.format(DateTimeFormatter.ISO_DATE);
    }

}
