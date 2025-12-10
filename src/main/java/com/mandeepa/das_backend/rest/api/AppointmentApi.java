package com.mandeepa.das_backend.rest.api;

import com.mandeepa.das_backend.dto.appointment.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Appointments")
@RequestMapping("/v1/appointments")
public interface AppointmentApi {

    @Operation(summary = "Create an appointment")
    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping(value = "/createAnAppointment", produces = "application/json")
    ResponseEntity<AppointmentResponse> createAppointment(@AuthenticationPrincipal User ud,
                                              @Valid @RequestBody AppointmentCreateRequest req);

    @Operation(summary = "Get appointments by role")
    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR')")
    @GetMapping("/getAppointment/me")
    ResponseEntity<Page<AppointmentResponse>> getAppointmentByRole(@AuthenticationPrincipal User ud,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "Cancel an appointment")
    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR')")
    @PutMapping("/{id}/cancel")
    ResponseEntity<AppointmentResponse> cancelAppointment(@PathVariable Long id,
                               @AuthenticationPrincipal User ud);

    @Operation(summary = "Update appointment status")
    @PreAuthorize("hasRole('DOCTOR')")
    @PutMapping("/{id}/status")
    ResponseEntity<AppointmentResponse> updateAppointmentStatus(@PathVariable Long id,
                                     @AuthenticationPrincipal User ud,
                                     @Valid @RequestBody AppointmentStatusUpdateRequest req);

    @Operation(summary = "Get appointment by id")
    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR','ADMIN')")
    @GetMapping("/getAppointmentById/{id}")
    ResponseEntity<AppointmentResponse> getAppointmentById(@PathVariable Long id,
                                @AuthenticationPrincipal User ud);
}
