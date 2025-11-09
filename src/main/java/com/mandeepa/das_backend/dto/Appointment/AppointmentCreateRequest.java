package com.mandeepa.das_backend.dto.Appointment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AppointmentCreateRequest {

    @NotNull
    private Long patientId;
    @NotNull
    private Long doctorId;
    @NotBlank
    private String startTime;
    @NotBlank
    private String endTime;
    private String notes;
}
