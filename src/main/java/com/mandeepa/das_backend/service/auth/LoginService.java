package com.mandeepa.das_backend.service.auth;

import com.mandeepa.das_backend.dto.UserDto;
import com.mandeepa.das_backend.server.JwtToken.JwtSignInDto;

public interface LoginService {

    UserDto signUp(UserDto user);

    JwtSignInDto signIn(UserDto user);
}
