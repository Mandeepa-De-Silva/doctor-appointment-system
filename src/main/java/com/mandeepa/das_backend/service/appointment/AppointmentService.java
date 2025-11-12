package com.mandeepa.das_backend.service.appointment;

import com.mandeepa.das_backend.dto.appointment.*;
import org.springframework.data.domain.Page;

public interface AppointmentService {

    AppointmentResponse create(String patientUsername, AppointmentCreateRequest req);

    Page<AppointmentResponse> listMine(String username, int page, int size);

    AppointmentResponse cancel(Long id, String username);

    AppointmentResponse updateStatus(Long id, String doctorUsername, String status);

    AppointmentResponse getById(Long id, String username);
}
