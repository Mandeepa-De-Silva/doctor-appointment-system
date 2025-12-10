package com.mandeepa.das_backend.service.auth;

import com.mandeepa.das_backend.constant.UserType;
import com.mandeepa.das_backend.dto.UserDto;
import com.mandeepa.das_backend.exception.DuplicateFoundException;
import com.mandeepa.das_backend.exception.UnAuthorizedException;
import com.mandeepa.das_backend.repository.PatientRepository;
import com.mandeepa.das_backend.repository.UserRepository;
import com.mandeepa.das_backend.server.JwtService;
import com.mandeepa.das_backend.server.JwtToken.JwtSignInDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PatientRepository patientRepository;
    private final UserDetailsService userDetailsService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override
    @Transactional
    public UserDto signUp(UserDto user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new DuplicateFoundException("Username already exists");
        }

        var entity = user.toEntity();
        entity.setPassword(encoder.encode(entity.getPassword()));
        entity.setCreatedAt(java.time.LocalDateTime.now());

        // Force default PATIENT if null to match assignment
        if (entity.getUserType() == null) {
            entity.setUserType(UserType.PATIENT);
        }

        var saved = userRepository.save(entity);

        if (saved.getUserType() == UserType.PATIENT) {
            patientRepository.save(
                    com.mandeepa.das_backend.entity.PatientEntity.builder()
                            .user(saved)
                            .phone(saved.getPhoneNumber())
                            .dob(saved.getDob())
                            .build()
            );
        }

        return UserDto.toDto(saved);
    }

    @Override
    public JwtSignInDto signIn(UserDto request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException ex) {
            throw new UnAuthorizedException("Invalid email or password");
        }

        UserDto userDto = UserDto.toDto(userRepository.findByUsername(request.getUsername()).orElseThrow());
        var user = userDetailsService.loadUserByUsername(request.getUsername());
        var jwt = jwtService.generateTokenWithRoles(userDto, user);
        JwtSignInDto jwtSignINResponse = JwtSignInDto.builder().token(jwt).build();
        log.info("User {} logged in at {}", userDto.getUsername(), java.time.LocalDateTime.now());
        return jwtSignINResponse;
    }


}
