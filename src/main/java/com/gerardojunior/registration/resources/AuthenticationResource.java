package com.gerardojunior.registration.resources;

import com.gerardojunior.registration.dto.AuthenticationRequest;
import com.gerardojunior.registration.services.AuthenticationService;
import com.gerardojunior.registration.util.StandardResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Auth management APIs")
public class AuthenticationResource {

    private final AuthenticationService service;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public StandardResponse authenticate(@RequestBody AuthenticationRequest request) {
        return new StandardResponse("AuthSuccessfully", "Authentication completed successfully", service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }

}
