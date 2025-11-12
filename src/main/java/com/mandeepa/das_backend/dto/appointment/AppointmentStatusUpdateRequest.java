package com.mandeepa.das_backend.dto.appointment;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AppointmentStatusUpdateRequest {

    @NotBlank(message = "Status cannot be blank")
    private String status; // CONFIRMED, REJECTED, COMPLETED, CANCELLED

}
