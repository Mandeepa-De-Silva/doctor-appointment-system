package com.mandeepa.das_backend.rest.controller;

import com.mandeepa.das_backend.dto.doctor.*;
import com.mandeepa.das_backend.rest.api.DoctorApi;
import com.mandeepa.das_backend.service.doctor.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class DoctorController implements DoctorApi {
    private final DoctorService doctorService;

    public ResponseEntity<DoctorPublicResponse> createDoctor(DoctorCreateRequest request) {
        DoctorPublicResponse response = doctorService.createDoctor(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<Page<DoctorPublicResponse>> getDoctorList(String name, Long specId, Pageable pageable) {
        Page<DoctorPublicResponse> response = doctorService.getDoctorList(name, specId, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<DoctorPublicResponse> getDoctorById(Long id) {
        DoctorPublicResponse response = doctorService.getDoctorById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<DoctorPublicResponse> updateDoctor(Long id, DoctorUpdateRequest request, User ud) {
        DoctorPublicResponse response = doctorService.updateDoctor(id, request, ud.getUsername());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
