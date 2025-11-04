package com.mandeepa.das_backend.rest.controller;

import com.mandeepa.das_backend.dto.doctor.*;
import com.mandeepa.das_backend.rest.api.DoctorApi;
import com.mandeepa.das_backend.service.doctor.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DoctorController implements DoctorApi {
    private final DoctorService service;

    public DoctorPublicResponse create(DoctorCreateRequest req) {
        return service.create(req);
    }

    public Page<DoctorPublicResponse> list(String name, Long specId, int page, int size) {
        return service.list(name, specId, page, size);
    }

    public DoctorPublicResponse get(Long id) {
        return service.get(id);
    }

    public DoctorPublicResponse update(Long id, DoctorUpdateRequest req, User ud) {
        return service.update(id, req, ud.getUsername());
    }
}
