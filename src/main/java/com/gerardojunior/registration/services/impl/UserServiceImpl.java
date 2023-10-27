package com.gerardojunior.registration.services.impl;

import com.gerardojunior.registration.config.JwtService;
import com.gerardojunior.registration.dto.AuthenticationResponse;
import com.gerardojunior.registration.dto.RegisterRequest;
import com.gerardojunior.registration.dto.UpdateRequest;
import com.gerardojunior.registration.dto.UserResponse;
import com.gerardojunior.registration.entity.meta.User;
import com.gerardojunior.registration.enums.Role;
import com.gerardojunior.registration.exception.NotFoundException;
import com.gerardojunior.registration.exception.ValidateException;
import com.gerardojunior.registration.mappers.IUserMapper;
import com.gerardojunior.registration.repositories.TokenRepository;
import com.gerardojunior.registration.repositories.UserRepository;
import com.gerardojunior.registration.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationServiceImpl authenticationService;
    private final IUserMapper mapper;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {

        if (18 >= ChronoUnit.YEARS.between(request.getDateOfBirth(), LocalDate.now())) {
            throw new ValidateException("You must be of legal age to register");
        }

        if (repository.countByEmailOrDocument(request.getEmail(), request.getDocument()) > 0 ) {
            throw new ValidateException("This user is already registered");
        }

        User user = repository.save(mapper.map(request, passwordEncoder.encode(request.getPassword())));

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        authenticationService.saveUserToken(user, jwtToken);

        return AuthenticationResponse.builder()
                                     .accessToken(jwtToken)
                                     .refreshToken(refreshToken)
                                     .build();
    }

    @Override
    public UserResponse find(String document) {
        User user = repository.findByDocument(document).orElseThrow(() -> new NotFoundException("User not found"));
        return mapper.map(user);
    }

    @Override
    public UserResponse update(String document, UpdateRequest request) {
        User user = repository.findByDocument(document).orElseThrow(() -> new NotFoundException("User not found"));
        return mapper.map(user);
    }

}
