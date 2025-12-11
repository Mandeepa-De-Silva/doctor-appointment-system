package com.mandeepa.das_backend.rest.api;

import com.mandeepa.das_backend.dto.prescription.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Prescriptions")
@RequestMapping("/v1/appointments/{appointmentId}/prescription")
public interface PrescriptionApi {

    @Operation(summary = "Create a prescription for an appointment")
    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping(value = "/createPrescription", produces = "application/json")
    ResponseEntity<PrescriptionResponse> createPrescription(@PathVariable Long appointmentId,
                                                           @AuthenticationPrincipal User ud,
                                                           @Valid @RequestBody PrescriptionCreateRequest req);

    @Operation(summary = "Get prescription for an appointment")
    @PreAuthorize("hasAnyRole('DOCTOR','PATIENT','ADMIN')")
    @GetMapping(value = "/getPrescription", produces = "application/json")
    ResponseEntity<PrescriptionResponse> getPrescription(@PathVariable Long appointmentId,
                             @AuthenticationPrincipal User ud);
}
