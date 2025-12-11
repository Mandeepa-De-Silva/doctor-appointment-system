package com.mandeepa.das_backend.entity;

import com.mandeepa.das_backend.constant.AppointmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "appointment")
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name="patient_id", nullable=false)
    private PatientEntity patient;

    @ManyToOne(optional = false)
    @JoinColumn(name="doctor_id", nullable=false)
    private DoctorEntity doctor;

    @Column(name="start_time", nullable=false)
    private LocalDateTime startTime;

    @Column(name="end_time", nullable=false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private AppointmentStatus status;

    @Column(length=1000)
    private String notes;
}
