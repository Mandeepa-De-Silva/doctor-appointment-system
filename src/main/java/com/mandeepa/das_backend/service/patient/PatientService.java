package com.mandeepa.das_backend.service.patient;

import  com.mandeepa.das_backend.dto.patient.*;

public interface PatientService {

    PatientMeResponse me(String username);

    PatientMeResponse updateMe(String username, PatientUpdateRequest req);
}

