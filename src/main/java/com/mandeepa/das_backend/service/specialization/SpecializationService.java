package com.mandeepa.das_backend.service.specialization;

import com.mandeepa.das_backend.dto.specialization.SpecializationCreateRequest;
import com.mandeepa.das_backend.dto.specialization.SpecializationResponse;
import com.mandeepa.das_backend.dto.specialization.SpecializationUpdateRequest;

import java.util.List;

public interface SpecializationService {
    SpecializationResponse create(SpecializationCreateRequest req);

    List<SpecializationResponse> list();

    SpecializationResponse update(Long id, SpecializationUpdateRequest req);

    void delete(Long id);
}
