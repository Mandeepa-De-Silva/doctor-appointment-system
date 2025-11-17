package com.mandeepa.das_backend.rest.controller;

import com.mandeepa.das_backend.dto.doctor.*;
import com.mandeepa.das_backend.rest.api.DoctorApi;
import com.mandeepa.das_backend.service.doctor.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class DoctorController implements DoctorApi {
    private final DoctorService doctorService;

    public ResponseEntity<DoctorPublicResponse> createDoctor(DoctorCreateRequest request) {
        log.info("Creating new doctor with name: {}", request.getFirstName() + " " + request.getLastName());
        DoctorPublicResponse response = doctorService.createDoctor(request);
        log.info("Doctor created successfully with : {}", response.getFullName());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<Page<DoctorPublicResponse>> getDoctorList(String name, Long specId, Pageable pageable) {
        log.info("Fetching doctor list with name: {} and specId: {}", name, specId);
        Page<DoctorPublicResponse> response = doctorService.getDoctorList(name, specId, pageable);
        log.info("Fetched {} doctors", response.getNumberOfElements());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<DoctorPublicResponse> getDoctorById(Long id) {
        log.info("Fetching doctor with ID: {}", id);
        DoctorPublicResponse response = doctorService.getDoctorById(id);
        log.info("Fetched doctor: {}", response.getFullName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<DoctorPublicResponse> updateDoctor(Long id, DoctorUpdateRequest request, User ud) {
        log.info("Updating doctor with ID: {} by user: {}", id, ud.getUsername());
        DoctorPublicResponse response = doctorService.updateDoctor(id, request, ud.getUsername());
        log.info("Doctor with ID: {} updated successfully", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
