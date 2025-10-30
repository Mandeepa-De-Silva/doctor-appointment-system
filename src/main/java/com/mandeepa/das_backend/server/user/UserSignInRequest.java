package com.mandeepa.das_backend.server.user;

import com.mandeepa.das_backend.dto.UserDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignInRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    public UserDto toDto() {
        return UserDto.builder()
                .username(username)
                .password(password)
                .build();
    }
}
