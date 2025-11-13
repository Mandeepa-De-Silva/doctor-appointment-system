package com.mandeepa.das_backend.rest.api;

import com.mandeepa.das_backend.dto.patient.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Patients")
@RequestMapping("/api/patients")
public interface PatientApi {

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/me")
    PatientMeResponse getPatientDetails(@AuthenticationPrincipal User ud);

    @PreAuthorize("hasRole('PATIENT')")
    @PutMapping("/me")
    PatientMeResponse updatePatient(@AuthenticationPrincipal User ud,
                               @Valid @RequestBody PatientUpdateRequest request);
}
