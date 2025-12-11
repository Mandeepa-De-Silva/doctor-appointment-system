package com.mandeepa.das_backend.dto.appointment;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentCreateRequest {

    @NotNull(message = "Doctor id cannot be null")
    private Long doctorId;

    @Future(message = "Start time must be in the future")
    private LocalDateTime startTime;

    @Future(message = "End time must be in the future")
    private LocalDateTime endTime;
    private String notes;
}
