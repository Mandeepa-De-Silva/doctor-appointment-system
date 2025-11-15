package com.mandeepa.das_backend.rest.controller;

import com.mandeepa.das_backend.dto.patient.*;
import com.mandeepa.das_backend.rest.api.PatientApi;
import com.mandeepa.das_backend.service.patient.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PatientController implements PatientApi {
    private final PatientService patientService;

    public ResponseEntity<PatientMeResponse> getPatientDetails(User userData) {
        PatientMeResponse response = patientService.getPatientDetails(userData.getUsername());
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<PatientMeResponse> updatePatient(User userData, PatientUpdateRequest request) {
        PatientMeResponse response = patientService.updatePatient(userData.getUsername(), request);
        return ResponseEntity.ok(response);
    }
}
