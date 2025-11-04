package com.mandeepa.das_backend.rest.controller;

import com.mandeepa.das_backend.dto.UserDto;
import com.mandeepa.das_backend.rest.api.LoginAPi;
import com.mandeepa.das_backend.server.JwtToken.JwtSignINResponse;
import com.mandeepa.das_backend.server.user.UserResponse;
import com.mandeepa.das_backend.server.user.UserSignInRequest;
import com.mandeepa.das_backend.server.user.UserSignupRequest;
import com.mandeepa.das_backend.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController implements LoginAPi {

    private final LoginService loginService;

    public ResponseEntity<UserResponse> signUp(UserSignupRequest request){
        log.info("User SignUp Request");
        UserDto userDTO = loginService.signUp(request.toDto());
        UserResponse userResponse = UserResponse.toResponse(userDTO);
        log.info("User Registered Successfully with: {}", userResponse.getUsername() + " , Time : " + userResponse.getCreatedAt());
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    public ResponseEntity<JwtSignINResponse> signIn(UserSignInRequest request){
        log.info("User SignIn Request");
        JwtSignINResponse jwtSignINResponse = JwtSignINResponse.fromToken(loginService.signIn(request.toDto()));
        log.info("User Signed In Successfully with:  {}", request.getUsername());
        return new ResponseEntity<>(jwtSignINResponse, HttpStatus.OK);
    }
}
