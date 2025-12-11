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
@RequestMapping("/v1/specializations")
@Tag(name = "Specializations")
public interface SpecializationApi {

    @Operation(summary = "Create a new specialization",security =  @SecurityRequirement(name = "Authorization"))
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/createSpecialization", produces = "application/json")
    ResponseEntity<SpecializationResponse> createSpecialization(@Valid @RequestBody SpecializationCreateRequest request);

    @Operation(summary = "Get all specializations", security = @SecurityRequirement(name = "Authorization"))
    @GetMapping(value = "/getAllSpecialization", produces = "application/json")
    ResponseEntity<List<SpecializationResponse>> getAllSpecialization();

    @Operation(summary = "Update a specialization",security =  @SecurityRequirement(name = "Authorization"))
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/updateSpecialization/{id}", produces = "application/json")
    ResponseEntity<SpecializationResponse> updateSpecialization(@PathVariable Long id, @Valid @RequestBody SpecializationUpdateRequest req);

    @Operation(summary = "Delete a specialization",security =  @SecurityRequirement(name = "Authorization"))
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/deleteSpecialization/{id}", produces = "application/json")
    ResponseEntity<Void> deleteSpecialization(@PathVariable Long id);
}
