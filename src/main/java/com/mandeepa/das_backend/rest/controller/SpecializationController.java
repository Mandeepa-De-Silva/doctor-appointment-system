package com.mandeepa.das_backend.rest.controller;

import com.mandeepa.das_backend.dto.specialization.SpecializationCreateRequest;
import com.mandeepa.das_backend.dto.specialization.SpecializationResponse;
import com.mandeepa.das_backend.dto.specialization.SpecializationUpdateRequest;
import com.mandeepa.das_backend.rest.api.SpecializationApi;
import com.mandeepa.das_backend.service.specialization.SpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SpecializationController implements SpecializationApi {

    private final SpecializationService specializationService;

    public ResponseEntity<SpecializationResponse> createSpecialization(SpecializationCreateRequest request) {
        SpecializationResponse response = specializationService.createSpecialization(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<List<SpecializationResponse>> getAllSpecialization() {
        List<SpecializationResponse> responseList = specializationService.getAllSpecialization();
        return ResponseEntity.ok(responseList);
    }


    public ResponseEntity<SpecializationResponse> updateSpecialization(Long id, SpecializationUpdateRequest request) {
        SpecializationResponse response = specializationService.updateSpecialization(id, request);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Void> deleteSpecialization(Long id) {
        specializationService.deleteSpecialization(id);
        return ResponseEntity.noContent().build();
    }
}
