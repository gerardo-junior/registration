package com.gerardojunior.registration.services.impl;

import com.gerardojunior.registration.config.JwtService;
import com.gerardojunior.registration.dto.*;
import com.gerardojunior.registration.entity.meta.User;
import com.gerardojunior.registration.enums.Role;
import com.gerardojunior.registration.exception.NotFoundException;
import com.gerardojunior.registration.exception.ValidateException;
import com.gerardojunior.registration.mappers.IUserMapper;
import com.gerardojunior.registration.repositories.UserRepository;
import com.gerardojunior.registration.services.AuthenticationService;
import com.gerardojunior.registration.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    private final IUserMapper mapper;

    @Transactional
    public UserResponse register(RegisterUserRequest request) {
        if (0 < repository.countByEmailOrDocument(request.getEmail(), request.getDocument())) {
            throw new ValidateException("UserAlreadyRegistered", "This user is already registered");
        }

        User user = mapper.map(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        user = repository.save(user);

        return mapper.map(user);
    }

    @Transactional
    public UserResponse update(String document, UpdateUserRequest request) {
        User user = repository.findByDocument(document).orElseThrow(() -> new NotFoundException("UserNotFound", "User not found"));
        mapper.merge(request, user);

        if (Objects.nonNull(request.getPassword()) && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        repository.save(user);

        return mapper.map(user);
    }

    @Transactional(readOnly = true)
    public UserResponse find(String document) {
        User user = repository.findByDocument(document).orElseThrow(() -> new NotFoundException("UserNotFound", "User not found"));
        return mapper.map(user);
    }


    @Transactional(readOnly = true)
    public Page<UserResponse> search(SearchUserRequest searchUserRequest, Pageable pageable) {
        return repository.findAll(searchUserRequest.toSpecification(), pageable).map(mapper::map);
    }

}
