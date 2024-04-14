package com.gerardojunior.registration.resources;

import com.gerardojunior.registration.dto.RegisterUserRequest;
import com.gerardojunior.registration.dto.SearchUserRequest;
import com.gerardojunior.registration.dto.UpdateUserRequest;
import com.gerardojunior.registration.dto.UserResponse;
import com.gerardojunior.registration.services.UserService;
import com.gerardojunior.registration.util.StandardResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User managment apis")
public class UserResource {

    private final UserService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public StandardResponse<List<UserResponse>> list(@RequestBody @Valid SearchUserRequest request, @PageableDefault Pageable pageable) {
        Page<UserResponse> users = service.findAll(request, pageable);
        return new StandardResponse<>("UserListed", "User Listed successfully", users.getContent(), users.getPageable());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StandardResponse<UserResponse> create(@RequestBody @Valid RegisterUserRequest request) {
        return new StandardResponse<>("UserCreated", "User created successfully", service.register(request));
    }

    @GetMapping("/{document}")
    @ResponseStatus(HttpStatus.OK)
    public StandardResponse<UserResponse> details(@PathVariable String document) {
        return new StandardResponse<>("UserFound", "User found successfully", service.find(document));
    }

    @PatchMapping("/{document}")
    @ResponseStatus(HttpStatus.OK)
    public StandardResponse<UserResponse> update(@PathVariable String document,
                                   @RequestBody @Valid UpdateUserRequest request) {
        return new StandardResponse<>("UserUpdated", "User updated successfully", service.update(document, request));
    }

}
