package com.gerardojunior.registration.entity;
import com.gerardojunior.registration.dto.UpdateUserRequest;
import com.gerardojunior.registration.entity.meta.User;
import com.gerardojunior.registration.enums.Gender;
import com.gerardojunior.registration.enums.Role;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        User user = new User();
        user.setId(1);
        user.setDocument("12345678901");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setDateOfBirth(LocalDate.of(1990, 1, 1));
        user.setAddress("123 Main St");
        user.setMobileNumber("1234567890");
        user.setGender(Gender.MALE);
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
        user.setRole(Role.ADMIN);

        // Assert
        assertEquals(1, user.getId());
        assertEquals("12345678901", user.getDocument());
        assertEquals("John", user.getFirstname());
        assertEquals("Doe", user.getLastname());
        assertEquals(LocalDate.of(1990, 1, 1), user.getDateOfBirth());
        assertEquals("123 Main St", user.getAddress());
        assertEquals("1234567890", user.getMobileNumber());
        assertEquals(Gender.MALE, user.getGender());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals(Role.ADMIN, user.getRole());
    }

    @Test
    void testMerge() {
        // Arrange
        User user = new User();
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setFirstname("UpdatedFirstName");
        updateUserRequest.setLastname("UpdatedLastName");
        updateUserRequest.setAddress("UpdatedAddress");
        updateUserRequest.setGender(Gender.FEMALE);
        updateUserRequest.setMobileNumber("9876543210");

        // Act
        user.merge(updateUserRequest);

        // Assert
        assertEquals("UpdatedFirstName", user.getFirstname());
        assertEquals("UpdatedLastName", user.getLastname());
        assertEquals("UpdatedAddress", user.getAddress());
        assertEquals(Gender.FEMALE, user.getGender());
        assertEquals("9876543210", user.getMobileNumber());
    }

    @Test
    void testAuthorities() {
        // Arrange
        User user = new User();
        user.setRole(Role.USER);

        // Act
        var authorities = user.getAuthorities();

        // Assert
        assertEquals(Collections.singletonList("ROLE_USER"), authorities);
    }
}
