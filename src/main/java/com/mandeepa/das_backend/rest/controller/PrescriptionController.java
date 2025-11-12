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

    private final PrescriptionService service;

    public PrescriptionResponse create(Long appointmentId, Long doctorId, User ud, PrescriptionCreateRequest req) {
        return service.create(appointmentId, ud.getUsername(), req);
    }

    public PrescriptionResponse get(Long appointmentId, User ud) {
        return service.get(appointmentId, ud.getUsername());
    }
}
