package com.mandeepa.das_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "doctor")
public class DoctorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    @Column(name="reg_no", nullable = false, unique = true)
    private String regNo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "specialization_id", nullable = false)
    private SpecializationEntity specialization;

    private Integer yearsOfExp;
    private String bio;
}
