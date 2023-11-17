package com.gerardojunior.registration.resources;

import com.gerardojunior.registration.dto.RegisterUserRequest;
import com.gerardojunior.registration.dto.SearchUserRequest;
import com.gerardojunior.registration.dto.UserResponse;
import com.gerardojunior.registration.services.UserService;
import com.gerardojunior.registration.util.StandardResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserResourceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserResource userResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listUsers() {
        // Mocking
        SearchUserRequest searchUserRequest = new SearchUserRequest();
        Pageable pageable = PageableDefault.of();
        Page<UserResponse> userResponsePage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(userService.search(searchUserRequest, pageable)).thenReturn(userResponsePage);

        // Test
        ResponseEntity<StandardResponse> responseEntity = userResource.list(searchUserRequest, pageable);

        // Verification
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("UserListed", responseEntity.getBody().getMeta().getCode());
        assertEquals("User Listed successfully", responseEntity.getBody().getMeta().getMessage());
        assertEquals(userResponsePage.getContent(), responseEntity.getBody().getData());
        assertEquals(userResponsePage.getPageable(), responseEntity.getBody().getMeta().getPageable());

        // Verify that the service method was called
        verify(userService, times(1)).search(searchUserRequest, pageable);
    }

    @Test
    void createUser() {
        // Mocking
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        when(userService.register(registerUserRequest)).thenReturn(new UserResponse());

        // Test
        ResponseEntity<StandardResponse> responseEntity = userResource.create(registerUserRequest);

        // Verification
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("UserCreated", responseEntity.getBody().getMeta().getCode());
        assertEquals("User created successfully", responseEntity.getBody().getMeta().getMessage());
        assertEquals(new UserResponse(), responseEntity.getBody().getData());

        // Verify that the service method was called
        verify(userService, times(1)).register(registerUserRequest);
    }

    // Similar tests for details() and update() methods can be added

}