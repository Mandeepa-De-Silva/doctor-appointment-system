package com.mandeepa.das_backend.service.prescription;

import com.mandeepa.das_backend.dto.prescription.*;

public interface PrescriptionService {

    PrescriptionResponse create(Long appointmentId, String doctorUsername, PrescriptionCreateRequest req);

    PrescriptionResponse get(Long appointmentId, String username);
}
