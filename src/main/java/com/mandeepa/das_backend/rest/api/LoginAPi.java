package com.mandeepa.das_backend.rest.api;

import com.mandeepa.das_backend.server.JwtToken.JwtSignINResponse;
import com.mandeepa.das_backend.server.user.UserResponse;
import com.mandeepa.das_backend.server.user.UserSignInRequest;
import com.mandeepa.das_backend.server.user.UserSignupRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Login", description = "This API facilitates for user login and authentication")
public interface LoginAPi {

    @SecurityRequirements
    @PostMapping("/signUp")
    ResponseEntity<UserResponse> signUp(@Valid @RequestBody UserSignupRequest request);

    @SecurityRequirements
    @PostMapping("/signIn")
    ResponseEntity<JwtSignINResponse> signIn(@Valid @RequestBody UserSignInRequest request);
}
