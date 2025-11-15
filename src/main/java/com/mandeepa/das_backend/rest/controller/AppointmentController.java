package com.mandeepa.das_backend.rest.controller;

import com.mandeepa.das_backend.dto.appointment.*;
import com.mandeepa.das_backend.rest.api.AppointmentApi;
import com.mandeepa.das_backend.service.appointment.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AppointmentController implements AppointmentApi {

    private final AppointmentService appointmentService;

    public ResponseEntity<AppointmentResponse> createAppointment(User ud, AppointmentCreateRequest req) {
        AppointmentResponse response = appointmentService.createAppointment(ud.getUsername(), req);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<Page<AppointmentResponse>> getAppointmentByRole(User ud, int page, int size) {
        Page<AppointmentResponse> response = appointmentService.getAppointmentByRole(ud.getUsername(), page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<AppointmentResponse> cancelAppointment(Long id, User ud) {
        AppointmentResponse response = appointmentService.cancelAppointment(id, ud.getUsername());
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<AppointmentResponse> updateAppointmentStatus(Long id, User ud, AppointmentStatusUpdateRequest req) {
        AppointmentResponse response = appointmentService.updateAppointmentStatus(id, ud.getUsername(), req.getStatus());
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<AppointmentResponse> getAppointmentById(Long id, User ud) {
        AppointmentResponse response = appointmentService.getAppointmentById(id, ud.getUsername());
        return ResponseEntity.ok(response);
    }
}
