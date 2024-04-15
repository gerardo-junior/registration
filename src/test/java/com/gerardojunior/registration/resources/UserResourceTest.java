package com.gerardojunior.registration.resources;

import com.gerardojunior.registration.dto.RegisterUserRequest;
import com.gerardojunior.registration.dto.SearchUserRequest;
import com.gerardojunior.registration.dto.UpdateUserRequest;
import com.gerardojunior.registration.dto.UserResponse;
import com.gerardojunior.registration.enums.Gender;
import com.gerardojunior.registration.helpers.RegisterUserRequestHelper;
import com.gerardojunior.registration.services.impl.UserServiceImpl;
import com.gerardojunior.registration.util.StandardResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserResourceTest {

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserResource userResource;

    @Test
    void testList() {
        List<UserResponse> userList = List.of(getUserResponse());
        Page<UserResponse> userPage = new PageImpl<>(userList);
        when(userService.findAll(any(SearchUserRequest.class), any(Pageable.class))).thenReturn(userPage);

        StandardResponse<List<UserResponse>> response = userResource.list(new SearchUserRequest(), Pageable.unpaged());
        assertEquals("UserListed", response.getMeta().getCode());
        assertEquals("User Listed successfully", response.getMeta().getMessage());
        assertFalse(response.getData().isEmpty());
        assertUser(response.getData().get(0));
    }

    @Test
    void testCreate() {
        RegisterUserRequest registerUserRequest = RegisterUserRequestHelper.createRegisterUserRequest();
        UserResponse userResponse = getUserResponse();
        when(userService.register(any(RegisterUserRequest.class))).thenReturn(userResponse);

        StandardResponse<UserResponse> response = userResource.create(registerUserRequest);
        assertEquals("UserCreated", response.getMeta().getCode());
        assertEquals("User created successfully", response.getMeta().getMessage());
        assertUser(response.getData());
    }

    @Test
    void testDetails() {
        UserResponse userResponse = getUserResponse();
        when(userService.find(anyString())).thenReturn(userResponse);
        
        StandardResponse<UserResponse> response = userResource.details("12332154875");
        assertEquals("UserFound", response.getMeta().getCode());
        assertEquals("User found successfully", response.getMeta().getMessage());
        assertUser(response.getData());
    }

    @Test
    void testUpdate() {
        UpdateUserRequest updateUserRequest = RegisterUserRequestHelper.createUpdateUserRequest();
        UserResponse userResponse = getUserResponse();
        when(userService.update(anyString(), any(UpdateUserRequest.class))).thenReturn(userResponse);

        StandardResponse<UserResponse> response = userResource.update("12332154875", updateUserRequest);
        assertEquals("UserUpdated", response.getMeta().getCode());
        assertEquals("User updated successfully", response.getMeta().getMessage());
        assertUser(response.getData());
    }
    
    private void assertUser(UserResponse userResponse) {
        assertEquals("123", userResponse.getId());
        assertEquals("12332154875", userResponse.getDocument());
        assertEquals("John", userResponse.getFirstname());
        assertEquals("Doe", userResponse.getLastname());
        assertEquals("1990-01-01", userResponse.getDateOfBirth());
        assertEquals("Dummy Address", userResponse.getAddress());
        assertEquals("1234567890", userResponse.getMobileNumber());
        assertEquals("UNKNOWN", userResponse.getGender());
    }

    private UserResponse getUserResponse() {
        UserResponse userResponse = new UserResponse();
        userResponse.setId("123");
        userResponse.setDocument("12332154875");
        userResponse.setFirstname("John");
        userResponse.setLastname("Doe");
        userResponse.setDateOfBirth(LocalDate.of(1990, 1, 1));
        userResponse.setAddress("Dummy Address");
        userResponse.setMobileNumber("1234567890");
        userResponse.setGender(Gender.UNKNOWN);
        return userResponse;
    }

}
