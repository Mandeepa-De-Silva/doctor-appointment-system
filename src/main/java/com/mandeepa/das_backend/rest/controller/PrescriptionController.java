package com.mandeepa.das_backend.rest.controller;

import com.mandeepa.das_backend.dto.prescription.*;
import com.mandeepa.das_backend.rest.api.PrescriptionApi;
import com.mandeepa.das_backend.service.prescription.PrescriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PrescriptionController implements PrescriptionApi {

    private final PrescriptionService prescriptionService;

    public ResponseEntity<PrescriptionResponse> createPrescription(Long appointmentId, User ud, PrescriptionCreateRequest request) {
        log.info("User {} is creating a prescription for appointment {}", ud.getUsername(), appointmentId);
        PrescriptionResponse response = prescriptionService.createPrescription(appointmentId, ud.getUsername(), request);
        log.info("Prescription created successfully with ID: {}", response.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<PrescriptionResponse> getPrescription(Long appointmentId, User ud) {
        log.info("User {} is fetching prescription for appointment {}", ud.getUsername(), appointmentId);
        PrescriptionResponse response = prescriptionService.getPrescription(appointmentId, ud.getUsername());
        log.info("Fetched prescription with ID {} successfully", response.getId());
        return ResponseEntity.ok(response);
    }
}
