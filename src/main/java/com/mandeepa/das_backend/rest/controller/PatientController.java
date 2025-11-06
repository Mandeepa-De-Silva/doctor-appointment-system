package com.mandeepa.das_backend.rest.controller;

import com.mandeepa.das_backend.dto.patient.*;
import com.mandeepa.das_backend.rest.api.PatientApi;
import com.mandeepa.das_backend.service.patient.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PatientController implements PatientApi {
    private final PatientService service;

    public PatientMeResponse me(User ud) {
        return service.me(ud.getUsername());
    }

    public PatientMeResponse updateMe(User ud, PatientUpdateRequest req) {
        return service.updateMe(ud.getUsername(), req);
    }
}
