package com.gerardojunior.registration.impl;

import com.gerardojunior.registration.dto.RegisterUserRequest;
import com.gerardojunior.registration.dto.SearchUserRequest;
import com.gerardojunior.registration.dto.UpdateUserRequest;
import com.gerardojunior.registration.dto.UserResponse;
import com.gerardojunior.registration.entity.meta.User;
import com.gerardojunior.registration.mappers.IUserMapper;
import com.gerardojunior.registration.repositories.UserRepository;
import com.gerardojunior.registration.helpers.RegisterUserRequestHelper;
import com.gerardojunior.registration.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private IUserMapper mapper;

    @InjectMocks
    private UserServiceImpl userService;

    private RegisterUserRequestHelper registerUserRequestHelper;

    @Test
    void testRegister() {
        RegisterUserRequest request = RegisterUserRequestHelper.createValidRequest();
        User user =  RegisterUserRequestHelper.createValidUser();
        when(repository.countByEmailOrDocument(any(), any())).thenReturn(0);
        when(repository.save(any())).thenReturn(user);
        when(mapper.map(request)).thenReturn(user);

        userService.register(request);

        verify(passwordEncoder).encode(any());
        verify(repository).save(user);
        verify(mapper).map(user);
    }

    @Test
    void testRegisterFail() {
        RegisterUserRequest request = RegisterUserRequestHelper.createValidRequest();
        User user =  RegisterUserRequestHelper.createValidUser();
        user.setId(UUID.randomUUID());
        when(repository.countByEmailOrDocument(any(), any())).thenReturn(1);

        assertThrows(Exception.class, () -> userService.register(request));

        verify(passwordEncoder, never()).encode(any());
        verify(repository, never()).save(any());
        verify(mapper, never()).map(user);
    }

    @Test
    void testUpdate() {
        String document = "document";
        UpdateUserRequest request = RegisterUserRequestHelper.UpdateValidRequest();
        User user = RegisterUserRequestHelper.createValidUser();
        when(repository.findByDocument(document)).thenReturn(java.util.Optional.of(user));

        userService.update(document, request);

        verify(mapper).merge(request, user);
        verify(repository).save(user);
        verify(mapper).map(user);
    }

    @Test
    void testFind() {
        String document = "document";
        User user =  RegisterUserRequestHelper.createValidUser();
        when(repository.findByDocument(document)).thenReturn(java.util.Optional.of(user));
        when(mapper.map(user)).thenReturn(new UserResponse(/* provide required parameters */));

        userService.find(document);

        verify(mapper).map(user);
    }

    @Test
    void testSearch() {
        SearchUserRequest searchUserRequest = new SearchUserRequest();
        Pageable pageable = Pageable.unpaged();
        Page<User> userPage = new PageImpl<>(List.of());
        when(repository.findAll(any(), any(Pageable.class))).thenReturn(userPage);

        userService.findAll(searchUserRequest, pageable);

        verify(repository).findAll(any(), any(Pageable.class));
        verify(mapper, times((int) userPage.getTotalElements())).map(any(User.class));
    }
}
