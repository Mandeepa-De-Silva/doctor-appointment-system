package com.mandeepa.das_backend.dto.appointment;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AppointmentStatusUpdateRequest {

    @NotBlank
    private String status; // CONFIRMED, REJECTED, COMPLETED, CANCELLED

}
