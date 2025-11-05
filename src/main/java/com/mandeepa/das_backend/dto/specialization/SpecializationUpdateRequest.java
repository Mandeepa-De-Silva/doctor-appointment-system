package com.mandeepa.das_backend.dto.specialization;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SpecializationUpdateRequest {
    @NotBlank
    private String name;
}
