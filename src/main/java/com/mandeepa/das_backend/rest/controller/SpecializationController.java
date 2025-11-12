package com.mandeepa.das_backend.rest.controller;

import com.mandeepa.das_backend.dto.specialization.SpecializationCreateRequest;
import com.mandeepa.das_backend.dto.specialization.SpecializationResponse;
import com.mandeepa.das_backend.dto.specialization.SpecializationUpdateRequest;
import com.mandeepa.das_backend.rest.api.SpecializationApi;
import com.mandeepa.das_backend.service.specialization.SpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SpecializationController implements SpecializationApi {

    private final SpecializationService service;

    public SpecializationResponse create(SpecializationCreateRequest req) {
        return service.create(req);
    }

    public List<SpecializationResponse> list() {
        return service.list();
    }


    public SpecializationResponse update(Long id, SpecializationUpdateRequest req) {
        return service.update(id, req);
    }

    public void delete(Long id) {
        service.delete(id);
    }
}
