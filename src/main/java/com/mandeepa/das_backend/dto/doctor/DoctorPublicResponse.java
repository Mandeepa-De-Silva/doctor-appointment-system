package com.mandeepa.das_backend.dto.doctor;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorPublicResponse {
    private Long id;
    private String fullName;
    private String regNo;
    private String specialization;
    private Integer yearsOfExp;
    private String bio;
}
