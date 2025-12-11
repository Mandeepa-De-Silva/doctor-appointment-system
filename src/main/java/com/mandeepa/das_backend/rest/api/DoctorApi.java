package com.mandeepa.das_backend.rest.api;

import com.mandeepa.das_backend.dto.doctor.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/doctors")
@Tag(name = "Doctors")
public interface DoctorApi {

    @Operation(summary = "Create a new doctor")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/createDoctor", produces = "application/json")
    ResponseEntity<DoctorPublicResponse> createDoctor(@Valid @RequestBody DoctorCreateRequest request);

    @Operation(summary = "Get all doctors")
    @GetMapping(value = "/getAllDoctors", produces = "application/json")
    ResponseEntity<Page<DoctorPublicResponse>> getDoctorList(@RequestParam(required = false) String name,
                                             @RequestParam(required = false) Long specId,
                                             Pageable pageable);

    @Operation(summary = "Get doctor by id")
    @GetMapping(value = "/getDoctorById/{id}", produces = "application/json")
    ResponseEntity<DoctorPublicResponse> getDoctorById(@PathVariable Long id);

    @Operation(summary = "Update doctor details")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @PutMapping(value = "/updateDoctorDetails/{id}", produces = "application/json")
    ResponseEntity<DoctorPublicResponse> updateDoctor(@PathVariable Long id,
                                @Valid @RequestBody DoctorUpdateRequest request,
                                @AuthenticationPrincipal User ud);
}
