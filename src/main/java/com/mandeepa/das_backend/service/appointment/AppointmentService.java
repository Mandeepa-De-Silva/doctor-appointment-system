package com.mandeepa.das_backend.service.appointment;

import com.mandeepa.das_backend.dto.appointment.*;
import org.springframework.data.domain.Page;

public interface AppointmentService {

    AppointmentResponse createAppointment(String patientUsername, AppointmentCreateRequest req);

    Page<AppointmentResponse> getAppointmentByRole(String username, int page, int size);

    AppointmentResponse cancelAppointment(Long id, String username);

    AppointmentResponse updateAppointmentStatus(Long id, String doctorUsername, String status);

    AppointmentResponse getAppointmentById(Long id, String username);
}
