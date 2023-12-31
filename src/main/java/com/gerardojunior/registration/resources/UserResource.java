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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User managment apis")
public class UserResource {

    private final UserService service;

    @GetMapping
    public ResponseEntity<StandardResponse> list(@RequestBody @Valid SearchUserRequest request, @PageableDefault Pageable pageable) {
        Page<UserResponse> users = service.search(request, pageable);
        return new ResponseEntity<>(new StandardResponse("UserListed", "User Listed successfully", users.getContent(), users.getPageable()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StandardResponse> create(@RequestBody @Valid RegisterUserRequest request) {
        return new ResponseEntity<>(new StandardResponse("UserCreated", "User created successfully", service.register(request)), HttpStatus.CREATED);
    }

    @GetMapping("/{document}")
    public ResponseEntity<StandardResponse> details(@PathVariable String document) {
        return new ResponseEntity<>(new StandardResponse("UserFound", "User found successfully", service.find(document)), HttpStatus.OK);
    }

    @PutMapping("/{document}")
    public ResponseEntity<StandardResponse> update(@PathVariable String document,
                                 @RequestBody @Valid UpdateUserRequest request) {
        return new ResponseEntity<>(new StandardResponse("UserUpdated", "User updated successfully", service.update(document, request)), HttpStatus.OK);
    }

}
