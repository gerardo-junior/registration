package com.gerardojunior.registration.services;

import com.gerardojunior.registration.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponse register(RegisterUserRequest request);

    UserResponse find(String document);

    UserResponse update(String document, UpdateUserRequest request);

    Page<UserResponse> findAll(SearchUserRequest searchUserRequest, Pageable pageable);
}
