package com.mandeepa.das_backend.rest.controller;

import com.mandeepa.das_backend.dto.prescription.*;
import com.mandeepa.das_backend.rest.api.PrescriptionApi;
import com.mandeepa.das_backend.service.prescription.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PrescriptionController implements PrescriptionApi {

    private final PrescriptionService prescriptionService;

    public PrescriptionResponse createPrescription(Long appointmentId, User ud, PrescriptionCreateRequest request) {
        return prescriptionService.createPrescription(appointmentId, ud.getUsername(), request);
    }

    public PrescriptionResponse getPrescription(Long appointmentId, User ud) {
        return prescriptionService.getPrescription(appointmentId, ud.getUsername());
    }
}
