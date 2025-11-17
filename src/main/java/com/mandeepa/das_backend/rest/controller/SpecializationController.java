package com.mandeepa.das_backend.rest.controller;

import com.mandeepa.das_backend.dto.specialization.SpecializationCreateRequest;
import com.mandeepa.das_backend.dto.specialization.SpecializationResponse;
import com.mandeepa.das_backend.dto.specialization.SpecializationUpdateRequest;
import com.mandeepa.das_backend.rest.api.SpecializationApi;
import com.mandeepa.das_backend.service.specialization.SpecializationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SpecializationController implements SpecializationApi {

    private final SpecializationService specializationService;

    public ResponseEntity<SpecializationResponse> createSpecialization(SpecializationCreateRequest request) {
        log.info("Creating new specialization with name: {}", request.getName());
        SpecializationResponse response = specializationService.createSpecialization(request);
        log.info("Specialization created successfully with name: {}", response.getName());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<List<SpecializationResponse>> getAllSpecialization() {
        log.info("Fetching all specializations");
        List<SpecializationResponse> responseList = specializationService.getAllSpecialization();
        log.info("Fetched {} specializations", responseList.size());
        return ResponseEntity.ok(responseList);
    }


    public ResponseEntity<SpecializationResponse> updateSpecialization(Long id, SpecializationUpdateRequest request) {
        log.info("Updating specialization with ID: {}", id);
        SpecializationResponse response = specializationService.updateSpecialization(id, request);
        log.info("Specialization with ID: {} updated successfully", id);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Void> deleteSpecialization(Long id) {
        log.info("Deleting specialization with ID: {}", id);
        specializationService.deleteSpecialization(id);
        log.info("Specialization with ID: {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }
}
