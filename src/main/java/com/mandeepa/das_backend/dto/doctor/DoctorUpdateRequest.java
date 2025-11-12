package com.mandeepa.das_backend.dto.doctor;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DoctorUpdateRequest {

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotNull(message = "Specialization cannot be null")
    private Long specializationId;

    @Min(message = "Years of experience cannot be negative", value = 0)
    private Integer yearsOfExp;
    private String bio;
}
