package com.mandeepa.das_backend.dto.prescription;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PrescriptionCreateRequest {

    @NotBlank(message = "Diagnosis cannot be blank")
    private String diagnosis;
    @NotBlank(message = "Advice cannot be blank")
    private String advice;
}
