package com.mandeepa.das_backend.repository;

import com.mandeepa.das_backend.entity.PrescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrescriptionRepository extends JpaRepository<PrescriptionEntity, Long> {

    boolean existsByAppointment_Id(Long appointmentId);

    Optional<PrescriptionEntity> findByAppointment_Id(Long appointmentId);
}
