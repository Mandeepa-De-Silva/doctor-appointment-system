package com.mandeepa.das_backend.entity;

import com.mandeepa.das_backend.constant.AppointmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Table(name="appointment",
        indexes = { @Index(name="idx_appt_doctor_start", columnList = "doctor_id,start_time"),
                @Index(name="idx_appt_patient_start", columnList = "patient_id,start_time") })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private OffsetDateTime startTime; // offset date time for timezone support with UTC

    @Column(name="end_time", nullable=false)
    private OffsetDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private AppointmentStatus status;

    @Column(length=1000)
    private String notes;
}
