package com.mandeepa.das_backend.rest.api;

import com.mandeepa.das_backend.dto.patient.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Patients")
@RequestMapping("/v1/patients")
public interface PatientApi {

    @Operation(summary = "Get patient details")
    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/getPatientDetail/me")
    ResponseEntity<PatientMeResponse> getPatientDetails(@AuthenticationPrincipal User ud);

    @Operation(summary = "Update patient details")
    @PreAuthorize("hasRole('PATIENT')")
    @PutMapping("/updatePatientDetails/me")
    ResponseEntity<PatientMeResponse> updatePatient(@AuthenticationPrincipal User ud,
                               @Valid @RequestBody PatientUpdateRequest request);
}
