package com.gerardojunior.registration.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerardojunior.registration.dto.RegisterUserRequest;
import com.gerardojunior.registration.dto.SearchUserRequest;
import com.gerardojunior.registration.dto.UpdateUserRequest;
import com.gerardojunior.registration.dto.UserResponse;
import com.gerardojunior.registration.entity.meta.User;
import com.gerardojunior.registration.helpers.RegisterUserRequestHelper;
import com.gerardojunior.registration.mappers.IUserMapper;
import com.gerardojunior.registration.repositories.UserRepository;
import com.gerardojunior.registration.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserResourceTest {

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserResource userResource;

    @Mock
    private UserRepository userRepository;

    @Mock
    private IUserMapper userMapper;

    private MockMvc mockMvc;

    public UserResourceTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userResource).build();
    }

    @Test
    void testList() throws Exception {
        List<UserResponse> userList = new ArrayList<>();
        Page<UserResponse> userPage = new PageImpl<>(userList);
        when(userService.findAll(any(SearchUserRequest.class), any(Pageable.class))).thenReturn(userPage);

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreate() throws Exception {
        RegisterUserRequest registerUserRequest = RegisterUserRequestHelper.createRegisterUserRequest();
        UserResponse userResponse = new UserResponse(/* Provide required parameters */);
        when(userService.register(any(RegisterUserRequest.class))).thenReturn(userResponse);

        mockMvc.perform(post("/api/v1/users")
                        .contentType("application/json")
                        .content(asJsonString(registerUserRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("UserCreated"))
                .andExpect(jsonPath("$.message").value("User created successfully"));
    }

    @Test
    void testDetails() throws Exception {
        String document = "dummy_document";
        UserResponse userResponse = new UserResponse();
        when(userRepository.findByDocument(any())).thenReturn(Optional.of(RegisterUserRequestHelper.createValidUser()));
        when(userMapper.map((User) any())).thenReturn(userResponse);
        mockMvc.perform(get("/api/v1/users/{document}", document))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdate() throws Exception {
        String document = "12332154875";
        UpdateUserRequest updateUserRequest = RegisterUserRequestHelper.createUpdateUserRequest();
        UserResponse userResponse = new UserResponse(/* Provide required parameters */);
        when(userRepository.findByDocument(any())).thenReturn(Optional.of(RegisterUserRequestHelper.createValidUser()));
        when(userMapper.map((User) any())).thenReturn(userResponse);

        mockMvc.perform(patch("/api/v1/users/{document}", document)
                        .contentType("application/json")
                        .content(asJsonString(updateUserRequest)))
                .andExpect(status().isOk());
    }

    private String asJsonString(final Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Erro ao converter objeto para JSON: " + e.getMessage(), e);
        }
    }
}
