package com.mandeepa.das_backend.dto.patient;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PatientUpdateRequest {

    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;
    private String phone;
    private String dob;
    private String address;
}
