package com.mandeepa.das_backend.service.doctor;

import com.mandeepa.das_backend.dto.doctor.*;
import org.springframework.data.domain.Page;

public interface DoctorService {

    DoctorPublicResponse create(DoctorCreateRequest req);

    Page<DoctorPublicResponse> list(String name, Long specId, int page, int size);

    DoctorPublicResponse get(Long id);

    DoctorPublicResponse update(Long id, DoctorUpdateRequest req, String actorUsername);
}
