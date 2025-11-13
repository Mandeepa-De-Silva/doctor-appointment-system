package com.mandeepa.das_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "specialization", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecializationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique = true)
    private String name;
}
