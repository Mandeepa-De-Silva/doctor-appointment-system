package com.mandeepa.das_backend.rest.controller;

import com.mandeepa.das_backend.dto.patient.*;
import com.mandeepa.das_backend.rest.api.PatientApi;
import com.mandeepa.das_backend.service.patient.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PatientController implements PatientApi {
    private final PatientService patientService;

    public ResponseEntity<PatientMeResponse> getPatientDetails(User userData) {
        log.info("Fetching patient details for user {}", userData.getUsername());
        PatientMeResponse response = patientService.getPatientDetails(userData.getUsername());
        log.info("Fetched patient details for user {}", userData.getUsername());
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<PatientMeResponse> updatePatient(User userData, PatientUpdateRequest request) {
        log.info("Updating patient details for user {}", userData.getUsername());
        PatientMeResponse response = patientService.updatePatient(userData.getUsername(), request);
        log.info("Updated patient details for user {}", userData.getUsername());
        return ResponseEntity.ok(response);
    }
}
