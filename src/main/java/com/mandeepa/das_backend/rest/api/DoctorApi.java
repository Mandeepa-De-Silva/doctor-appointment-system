package com.mandeepa.das_backend.rest.api;

import com.mandeepa.das_backend.dto.doctor.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctors")
@Tag(name = "Doctors")
public interface DoctorApi {

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    DoctorPublicResponse createDoctor(@Valid @RequestBody DoctorCreateRequest request);

    @GetMapping()
    Page<DoctorPublicResponse> getDoctorList(@RequestParam(required = false) String name,
                                             @RequestParam(required = false) Long specId,
                                             Pageable pageable);

    @GetMapping("/{id}")
    DoctorPublicResponse getDoctorById(@PathVariable Long id);

    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @PutMapping("/{id}")
    DoctorPublicResponse updateDoctor(@PathVariable Long id,
                                @Valid @RequestBody DoctorUpdateRequest request,
                                @AuthenticationPrincipal User ud);
}
