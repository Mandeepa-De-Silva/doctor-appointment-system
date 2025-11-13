package com.mandeepa.das_backend.service.prescription;

import com.mandeepa.das_backend.dto.prescription.*;

public interface PrescriptionService {

    PrescriptionResponse createPrescription(Long appointmentId, String doctorUsername, PrescriptionCreateRequest request);

    PrescriptionResponse getPrescription(Long appointmentId, String username);
}
