package com.mandeepa.das_backend.dto.prescription;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrescriptionResponse {

    private Long id;
    private Long appointmentId;
    private String diagnosis;
    private String advice;
    private String createdAt;
}
