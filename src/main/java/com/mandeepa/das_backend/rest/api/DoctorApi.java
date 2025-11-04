package com.mandeepa.das_backend.rest.api;

import com.mandeepa.das_backend.dto.doctor.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Doctors")
@RequestMapping("/api/doctors")
public interface DoctorApi {

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    DoctorPublicResponse create(@Valid @RequestBody DoctorCreateRequest req);

    @GetMapping
    Page<DoctorPublicResponse> list(@RequestParam(required = false) String name,
                                    @RequestParam(required = false) Long specId,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size);

    @GetMapping("/{id}")
    DoctorPublicResponse get(@PathVariable Long id);

    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @PutMapping("/{id}")
    DoctorPublicResponse update(@PathVariable Long id,
                                @Valid @RequestBody DoctorUpdateRequest req,
                                @org.springframework.security.core.annotation.AuthenticationPrincipal org.springframework.security.core.userdetails.User ud);
}
