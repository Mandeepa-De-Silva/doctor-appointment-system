package com.mandeepa.das_backend.dto.appointment;

import com.mandeepa.das_backend.constant.AppointmentStatus;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentResponse {

    private Long id;
    private Long doctorId;
    private Long patientId;
    private String startTime;
    private String endTime;
    private AppointmentStatus status;
    private String notes;
}
