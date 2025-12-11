package com.mandeepa.das_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "prescription")
public class PrescriptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name="appointment_id", nullable=false, unique = true)
    private AppointmentEntity appointment;

    @Column(nullable=false, length=1000)
    private String diagnosis;

    @Column(nullable=false, length=2000)
    private String advice;

    @CreationTimestamp
    private Instant createdAt;
}
