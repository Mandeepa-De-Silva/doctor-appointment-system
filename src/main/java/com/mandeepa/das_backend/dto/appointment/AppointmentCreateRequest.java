package com.mandeepa.das_backend.dto.appointment;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class AppointmentCreateRequest {

    @NotNull(message = "Doctor id cannot be null")
    private Long doctorId;

    @Future(message = "Start time must be in the future")
    private OffsetDateTime startTime;

    @Future(message = "End time must be in the future")
    private OffsetDateTime endTime;
    private String notes;
}
