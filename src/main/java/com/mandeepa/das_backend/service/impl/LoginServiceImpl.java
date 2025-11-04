package com.mandeepa.das_backend.service.impl;

import com.mandeepa.das_backend.dto.UserDto;
import com.mandeepa.das_backend.exception.DuplicateFoundException;
import com.mandeepa.das_backend.repository.UserRepository;
import com.mandeepa.das_backend.server.JwtService;
import com.mandeepa.das_backend.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override
    public UserDto signUp(UserDto user) {
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            log.info("User with username {} already exists", user.getUsername());
            throw new DuplicateFoundException("Username already exists");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        return UserDto.toDto(userRepository.save(user.toEntity()));
    }

    @Override
    public String signIn(UserDto user) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user.getUsername());
        } else {
            return "Fail";
        }
    }


}
