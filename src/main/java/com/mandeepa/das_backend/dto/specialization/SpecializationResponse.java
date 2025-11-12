package com.mandeepa.das_backend.dto.specialization;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpecializationResponse {
    private Long id;
    private String name;
}
