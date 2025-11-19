package com.mandeepa.das_backend.service.auth;

import com.mandeepa.das_backend.dto.UserDto;

public interface LoginService {

    UserDto signUp(UserDto user);

    String signIn(UserDto user);
}
