package com.mandeepa.das_backend.service.doctor;

import com.mandeepa.das_backend.dto.doctor.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DoctorService {

    DoctorPublicResponse createDoctor(DoctorCreateRequest request);

    Page<DoctorPublicResponse> getDoctorList(String name, Long specId, Pageable pageable);

    DoctorPublicResponse getDoctorById(Long id);

    DoctorPublicResponse updateDoctor(Long id, DoctorUpdateRequest request, String actorUsername);
}
