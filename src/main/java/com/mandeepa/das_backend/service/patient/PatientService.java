package com.mandeepa.das_backend.service.patient;

import  com.mandeepa.das_backend.dto.patient.*;

public interface PatientService {

    PatientMeResponse getPatientDetails(String username);

    PatientMeResponse updatePatient(String username, PatientUpdateRequest request);
}

