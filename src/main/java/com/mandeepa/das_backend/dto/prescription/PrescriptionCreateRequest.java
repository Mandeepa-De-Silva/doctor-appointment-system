package com.mandeepa.das_backend.dto.prescription;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PrescriptionCreateRequest {

    @NotBlank
    private String diagnosis;
    @NotBlank
    private String advice;
}
