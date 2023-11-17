package com.gerardojunior.registration.dto;

import com.gerardojunior.registration.enums.Gender;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RegisterUserRequestTest {

    @Test
    void testGetters() {
        // Arrange
        RegisterUserRequest request = RegisterUserRequest.builder()
                .document("12345678901")
                .firstname("John")
                .lastname("Doe")
                .dateOfBirth("2000-01-01")
                .email("john.doe@example.com")
                .password("Passw0rd!")
                .address("123 Main St")
                .mobileNumber("1234567890")
                .gender("MALE")
                .build();

        // Act
        String document = request.getDocument();
        String firstname = request.getFirstname();
        String lastname = request.getLastname();
        String dateOfBirth = request.getDateOfBirth().toString();
        String email = request.getEmail();
        String password = request.getPassword();
        String address = request.getAddress();
        String mobileNumber = request.getMobileNumber();
        Gender gender = request.getGender();

        // Assert
        assertEquals("12345678901", document);
        assertEquals("John", firstname);
        assertEquals("Doe", lastname);
        assertEquals("2000-01-01", dateOfBirth);
        assertEquals("john.doe@example.com", email);
        assertEquals("Passw0rd!", password);
        assertEquals("123 Main St", address);
        assertEquals("1234567890", mobileNumber);
        assertEquals(Gender.MALE, gender);
    }

    @Test
    void testGetGenderWithInvalidValue() {
        // Arrange
        RegisterUserRequest request = RegisterUserRequest.builder()
                .gender("INVALID_GENDER")
                .build();

        // Act
        Gender gender = request.getGender();

        // Assert
        assertEquals(Gender.UNKNOWN, gender);
    }
}