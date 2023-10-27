package com.gerardojunior.registration.resources;

import com.gerardojunior.registration.dto.RegisterRequest;
import com.gerardojunior.registration.dto.UpdateRequest;
import com.gerardojunior.registration.services.UserService;
import com.gerardojunior.registration.util.StandardResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User managment apis")
public class UserResource {

    private final UserService service;

    @ApiResponses({ @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) }),
                    @ApiResponse(responseCode = "403", content = { @Content(schema = @Schema()) }),
                    @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
                    @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping
    public ResponseEntity<String> list() {
        return new ResponseEntity(new StandardResponse("200", "Done", "Hello from secured endpoint"), HttpStatus.OK);
    }

    @GetMapping("/{document}")
    public ResponseEntity details(@PathVariable String document) {
        return new ResponseEntity(new StandardResponse("200", "Done", service.find(document)), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity create(@RequestBody @Valid RegisterRequest request) {
        return new ResponseEntity(new StandardResponse("201", "Done", service.register(request)), HttpStatus.CREATED);
    }

    @PutMapping("/{document}")
    public ResponseEntity<String> update(@PathVariable String document, @RequestBody @Valid UpdateRequest request) {
        return new ResponseEntity(new StandardResponse("200", "Done", service.update(document, request)), HttpStatus.OK);
    }

}
