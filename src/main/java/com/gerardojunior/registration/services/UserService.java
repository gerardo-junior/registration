package com.gerardojunior.registration.services;

import com.gerardojunior.registration.dto.UpdateRequest;
import com.gerardojunior.registration.dto.UserResponse;
import com.gerardojunior.registration.dto.AuthenticationResponse;
import com.gerardojunior.registration.dto.RegisterRequest;

public interface UserService {

    AuthenticationResponse register(RegisterRequest request);

    UserResponse find(String document);

    UserResponse update(String document, UpdateRequest request);
}
