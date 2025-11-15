package com.mandeepa.das_backend.rest.api;

import com.mandeepa.das_backend.dto.prescription.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Prescriptions")
@RequestMapping("/api/appointments/{appointmentId}/prescription")
public interface PrescriptionApi {

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping
    ResponseEntity<PrescriptionResponse> createPrescription(@PathVariable Long appointmentId,
                                                           @AuthenticationPrincipal User ud,
                                                           @Valid @RequestBody PrescriptionCreateRequest req);

    @PreAuthorize("hasAnyRole('DOCTOR','PATIENT','ADMIN')")
    @GetMapping
    ResponseEntity<PrescriptionResponse> getPrescription(@PathVariable Long appointmentId,
                             @AuthenticationPrincipal User ud);
}
