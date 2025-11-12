package com.mandeepa.das_backend.dto.Appointment;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AppointmentStatusUpdateRequest {

    @NotBlank
    private String status; // CONFIRMED, REJECTED, COMPLETED, CANCELLED

}
