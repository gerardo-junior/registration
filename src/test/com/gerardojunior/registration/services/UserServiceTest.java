package com.gerardojunior.registration.services;

import com.gerardojunior.registration.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    AuthenticationResponse register(RegisterUserRequest request);

    UserResponse find(String document);

    UserResponse update(String document, UpdateUserRequest request);

    Page<UserResponse> search(SearchUserRequest searchUserRequest, Pageable pageable);
}
