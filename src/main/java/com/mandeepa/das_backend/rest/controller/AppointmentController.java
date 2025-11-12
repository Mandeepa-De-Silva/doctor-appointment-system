package com.mandeepa.das_backend.rest.controller;

import com.mandeepa.das_backend.dto.appointment.*;
import com.mandeepa.das_backend.rest.api.AppointmentApi;
import com.mandeepa.das_backend.service.appointment.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AppointmentController implements AppointmentApi {

    private final AppointmentService appointmentService;

    public AppointmentResponse create(User ud, AppointmentCreateRequest req) {
        return appointmentService.create(ud.getUsername(), req);
    }

    public Page<AppointmentResponse> listMine(User ud, int page, int size) {
        return appointmentService.listMine(ud.getUsername(), page, size);
    }

    public AppointmentResponse cancel(Long id, User ud) {
        return appointmentService.cancel(id, ud.getUsername());
    }

    public AppointmentResponse updateStatus(Long id, User ud, AppointmentStatusUpdateRequest req) {
        return appointmentService.updateStatus(id, ud.getUsername(), req.getStatus());
    }

    public AppointmentResponse getById(Long id, User ud) {
        return appointmentService.getById(id, ud.getUsername());
    }
}
