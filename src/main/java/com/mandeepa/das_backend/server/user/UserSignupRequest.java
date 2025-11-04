package com.mandeepa.das_backend.server.user;

import com.mandeepa.das_backend.constant.UserType;
import com.mandeepa.das_backend.dto.UserDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupRequest {

    private String firstName;
    private String lastName;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String username;
    private String password;
    private UserType userType;

    public UserDto toDto(){
        return UserDto.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .password(password)
                .userType(userType)
                .build();
    }
}
