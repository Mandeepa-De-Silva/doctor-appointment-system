package com.mandeepa.das_backend.service.specialization;

import com.mandeepa.das_backend.dto.specialization.SpecializationCreateRequest;
import com.mandeepa.das_backend.dto.specialization.SpecializationResponse;
import com.mandeepa.das_backend.dto.specialization.SpecializationUpdateRequest;

import java.util.List;

public interface SpecializationService {

    SpecializationResponse createSpecialization(SpecializationCreateRequest request);

    List<SpecializationResponse> getAllSpecialization();

    SpecializationResponse updateSpecialization(Long id, SpecializationUpdateRequest request);

    void deleteSpecialization(Long id);
}
