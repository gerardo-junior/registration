package com.gerardojunior.registration.helpers;

import com.gerardojunior.registration.dto.RegisterUserRequest;
import com.gerardojunior.registration.dto.UpdateUserRequest;
import com.gerardojunior.registration.entity.meta.User;
import com.gerardojunior.registration.enums.Gender;
import com.gerardojunior.registration.enums.Role;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class RegisterUserRequestHelper {

    public static RegisterUserRequest createValidRequest() {
        return RegisterUserRequest.builder()
                .document("12345678900")
                .firstname("John")
                .lastname("Doe")
                .dateOfBirth("1980-01-01")
                .email("john.doe@example.com")
                .password("Password1!")
                .address("123 Main St")
                .mobileNumber("1234567890")
                .gender(Gender.MALE.name())
                .build();
    }

    public static UpdateUserRequest UpdateValidRequest() {
        return UpdateUserRequest.builder()
                .address("123 Main St")
                .password("123321")
                .build();
    }


    public static User createValidUser() {
        return User.builder()
                .id(UUID.randomUUID())
                .document("12345678900")
                .firstname("John")
                .lastname("Doe")
                .dateOfBirth(LocalDate.of(1980, 1, 1))
                .address("123 Main St")
                .mobileNumber("1234567890")
                .gender(Gender.MALE)
                .email("john.doe@example.com")
                .password("Password1!")
                .role(Role.USER)
                .build();
    }

    public static UpdateUserRequest createUpdateUserRequest() {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setFirstname("Updated Firstname");
        request.setPassword("123Gj@nm");
        request.setLastname("Updated Lastname");
        request.setAddress("Updated Address");
        request.setMobileNumber("9876543210");
        request.setGender(Gender.MALE);
        return request;
    }

    public static RegisterUserRequest createRegisterUserRequest() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setDocument("12345678900");
        request.setFirstname("John");
        request.setLastname("Doe");
        request.setDateOfBirth("1996-06-06");
        request.setEmail("john.doe@example.com");
        request.setPassword("Password123!");
        request.setAddress("123 Main St");
        request.setMobileNumber("1234567890");
        request.setGender("MALE");
        return request;
    }


}
