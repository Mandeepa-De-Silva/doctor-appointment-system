package com.mandeepa.das_backend.dto.doctor;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DoctorCreateRequest {

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @Email(message = "Invalid email address or format")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @Size(min=6, message="Password must be at least 6 characters long")
    @NotBlank(message = "Password cannot be blank")
    private String password;

    @NotBlank(message = "Reg no cannot be blank")
    private String regNo;

    @NotNull(message = "Specialization cannot be null")
    private Long specializationId;
    @Min(message = "Years of experience cannot be negative", value = 0)
    private Integer yearsOfExp;
    private String bio;
}
