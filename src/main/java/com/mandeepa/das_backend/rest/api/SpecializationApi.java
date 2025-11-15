package com.mandeepa.das_backend.rest.api;

import com.mandeepa.das_backend.dto.specialization.SpecializationCreateRequest;
import com.mandeepa.das_backend.dto.specialization.SpecializationResponse;
import com.mandeepa.das_backend.dto.specialization.SpecializationUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specializations")
@Tag(name = "Specializations")
public interface SpecializationApi {

    @Operation(summary = "Create a new specialization",security =  @SecurityRequirement(name = "Authorization"))
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    ResponseEntity<SpecializationResponse> createSpecialization(@Valid @RequestBody SpecializationCreateRequest request);

    @GetMapping
    ResponseEntity<List<SpecializationResponse>> getAllSpecialization();

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    ResponseEntity<SpecializationResponse> updateSpecialization(@PathVariable Long id, @Valid @RequestBody SpecializationUpdateRequest req);

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteSpecialization(@PathVariable Long id);
}
