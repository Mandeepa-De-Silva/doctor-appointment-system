package com.mandeepa.das_backend.rest.api;

import com.mandeepa.das_backend.dto.Appointment.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Appointments")
@RequestMapping("/api/appointments")
public interface AppointmentApi {

    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping
    AppointmentResponse create(@AuthenticationPrincipal User ud,
                               @Valid @RequestBody AppointmentCreateRequest req);

    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR')")
    @GetMapping("/me")
    Page<AppointmentResponse> listMine(@AuthenticationPrincipal User ud,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size);

    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR')")
    @PutMapping("/{id}/cancel")
    AppointmentResponse cancel(@PathVariable Long id,
                               @AuthenticationPrincipal User ud);

    @PreAuthorize("hasRole('DOCTOR')")
    @PutMapping("/{id}/status")
    AppointmentResponse updateStatus(@PathVariable Long id,
                                     @AuthenticationPrincipal User ud,
                                     @Valid @RequestBody AppointmentStatusUpdateRequest req);

    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR','ADMIN')")
    @GetMapping("/{id}")
    AppointmentResponse getById(@PathVariable Long id,
                                @AuthenticationPrincipal User ud);
}
