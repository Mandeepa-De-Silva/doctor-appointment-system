package com.mandeepa.das_backend.dto.patient;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientMeResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String phone;
    private String dob;
    private String address;
}
