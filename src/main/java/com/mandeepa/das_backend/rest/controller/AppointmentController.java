package com.mandeepa.das_backend.rest.controller;

import com.mandeepa.das_backend.dto.appointment.*;
import com.mandeepa.das_backend.rest.api.AppointmentApi;
import com.mandeepa.das_backend.service.appointment.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AppointmentController implements AppointmentApi {

    private final AppointmentService appointmentService;

    public ResponseEntity<AppointmentResponse> createAppointment(User ud, AppointmentCreateRequest req) {
        log.info("User {} is creating an appointment", ud.getUsername());
        AppointmentResponse response = appointmentService.createAppointment(ud.getUsername(), req);
        log.info("Appointment created successfully with ID: {}", response.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<Page<AppointmentResponse>> getAppointmentByRole(User ud, int page, int size) {
        log.info("User {} is fetching appointments", ud.getUsername());
        Page<AppointmentResponse> response = appointmentService.getAppointmentByRole(ud.getUsername(), page, size);
        log.info("Fetched {} appointments for user {}", response.getNumberOfElements(), ud.getUsername());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<AppointmentResponse> cancelAppointment(Long id, User ud) {
        log.info("User {} is cancelling appointment with ID {}", ud.getUsername(), id);
        AppointmentResponse response = appointmentService.cancelAppointment(id, ud.getUsername());
        log.info("Appointment with ID {} cancelled successfully", id);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<AppointmentResponse> updateAppointmentStatus(Long id, User ud, AppointmentStatusUpdateRequest req) {
        log.info("Doctor {} is updating status of appointment ID {} to {}", ud.getUsername(), id, req.getStatus());
        AppointmentResponse response = appointmentService.updateAppointmentStatus(id, ud.getUsername(), req.getStatus());
        log.info("Appointment ID {} status updated successfully to {}", id, req.getStatus());
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<AppointmentResponse> getAppointmentById(Long id, User ud) {
        log.info("User {} is fetching appointment with ID {}", ud.getUsername(), id);
        AppointmentResponse response = appointmentService.getAppointmentById(id, ud.getUsername());
        log.info("Fetched appointment with ID {} successfully", id);
        return ResponseEntity.ok(response);
    }
}
