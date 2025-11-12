package com.mandeepa.das_backend.dto.patient;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PatientUpdateRequest {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String phone;
    private String dob;
    private String address;
}
