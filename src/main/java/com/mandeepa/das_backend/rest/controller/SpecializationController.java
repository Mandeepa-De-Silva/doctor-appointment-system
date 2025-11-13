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

    private final SpecializationService specializationService;

    public SpecializationResponse createSpecialization(SpecializationCreateRequest request) {
        return specializationService.createSpecialization(request);
    }

    public List<SpecializationResponse> getAllSpecialization() {
        return specializationService.getAllSpecialization();
    }


    public SpecializationResponse updateSpecialization(Long id, SpecializationUpdateRequest request) {
        return specializationService.updateSpecialization(id, request);
    }

    public void deleteSpecialization(Long id) {
        specializationService.deleteSpecialization(id);
    }
}
