package com.mandeepa.das_backend.service;

import com.mandeepa.das_backend.dto.UserDto;

public interface LoginService {

    UserDto signUp(UserDto user);

    String signIn(UserDto user);
}
