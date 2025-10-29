package com.mandeepa.das_backend.dto;

import com.mandeepa.das_backend.constant.UserType;
import com.mandeepa.das_backend.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String phoneNumber;
    private String address;
    private String dob;
    private UserType userType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public static UserDto toDto(UserEntity user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .dob(user.getDob())
                .userType(user.getUserType()) // Assuming UserType is an enum
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .password(password)
                .phoneNumber(phoneNumber)
                .address(address)
                .dob(dob)
                .userType(userType) // Assuming UserType is an enum
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
