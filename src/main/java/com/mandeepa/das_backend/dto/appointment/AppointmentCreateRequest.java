package com.mandeepa.das_backend.dto.appointment;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AppointmentCreateRequest {

    @NotNull(message = "Patient id cannot be null")
    private Long patientId;

    @NotNull(message = "Doctor id cannot be null")
    private Long doctorId;

    @NotBlank(message = "Start time cannot be blank")
    @Future(message = "Start time must be in the future")
    private String startTime;

    @NotBlank(message = "End time cannot be blank")
    @Future(message = "End time must be in the future")
    private String endTime;
    private String notes;
}
