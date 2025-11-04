package com.mandeepa.das_backend.dto.doctor;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DoctorCreateRequest {
    @NotBlank private String firstName;
    @NotBlank private String lastName;
    @Email @NotBlank private String email;
    @Size(min=6) @NotBlank private String password;
    @NotBlank private String regNo;
    @NotNull private Long specializationId;
    @Min(0) private Integer yearsOfExp;
    private String bio;
}
