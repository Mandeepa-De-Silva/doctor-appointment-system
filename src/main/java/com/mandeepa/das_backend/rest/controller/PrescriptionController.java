package com.mandeepa.das_backend.rest.controller;

import com.mandeepa.das_backend.dto.prescription.*;
import com.mandeepa.das_backend.rest.api.PrescriptionApi;
import com.mandeepa.das_backend.service.prescription.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PrescriptionController implements PrescriptionApi {

    private final PrescriptionService prescriptionService;

    public ResponseEntity<PrescriptionResponse> createPrescription(Long appointmentId, User ud, PrescriptionCreateRequest request) {
        PrescriptionResponse response = prescriptionService.createPrescription(appointmentId, ud.getUsername(), request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<PrescriptionResponse> getPrescription(Long appointmentId, User ud) {
        PrescriptionResponse response = prescriptionService.getPrescription(appointmentId, ud.getUsername());
        return ResponseEntity.ok(response);
    }
}
