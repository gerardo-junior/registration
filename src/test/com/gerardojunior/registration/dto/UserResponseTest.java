package com.gerardojunior.registration.dto;
import com.gerardojunior.registration.enums.Gender;
import com.gerardojunior.registration.enums.Role;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserResponseTest {

    @Test
    void testGetGender() {
        UserResponse userResponse = new UserResponse();
        userResponse.setGender(Gender.MALE);
        assertEquals("MALE", userResponse.getGender());
    }

    @Test
    void testGetRole() {
        UserResponse userResponse = new UserResponse();
        userResponse.setRole(Role.ADMIN);
        assertEquals("ADMIN", userResponse.getRole());
    }

    @Test
    void testGetDateOfBirth() {
        UserResponse userResponse = new UserResponse();
        userResponse.setDateOfBirth(LocalDate.of(1990, 1, 1));
        assertEquals("1990-01-01", userResponse.getDateOfBirth());
    }

}