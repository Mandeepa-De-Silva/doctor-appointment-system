package com.mandeepa.das_backend.rest.controller;

import com.mandeepa.das_backend.dto.doctor.*;
import com.mandeepa.das_backend.rest.api.DoctorApi;
import com.mandeepa.das_backend.service.doctor.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class DoctorController implements DoctorApi {
    private final DoctorService doctorService;

    public DoctorPublicResponse createDoctor(DoctorCreateRequest request) {
        return doctorService.createDoctor(request);
    }

    public Page<DoctorPublicResponse> getDoctorList(String name, Long specId, Pageable pageable) {
        return doctorService.getDoctorList(name, specId, pageable);
    }

    public DoctorPublicResponse getDoctorById(Long id) {
        return doctorService.getDoctorById(id);
    }

    public DoctorPublicResponse updateDoctor(Long id, DoctorUpdateRequest request, User ud) {
        return doctorService.updateDoctor(id, request, ud.getUsername());
    }
}
