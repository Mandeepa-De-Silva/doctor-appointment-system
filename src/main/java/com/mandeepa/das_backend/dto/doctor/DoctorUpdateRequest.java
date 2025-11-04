package com.mandeepa.das_backend.dto.doctor;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DoctorUpdateRequest {
    @NotBlank private String firstName;
    @NotBlank private String lastName;
    @NotNull private Long specializationId;
    @Min(0) private Integer yearsOfExp;
    private String bio;
}
