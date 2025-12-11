package com.mandeepa.das_backend.repository;

import com.mandeepa.das_backend.entity.AppointmentEntity;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;


public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

    @Query("""
        select count(a) > 0 from appointment a
        where a.doctor.id = :doctorId
          and a.status in (com.mandeepa.das_backend.constant.AppointmentStatus.PENDING,
                           com.mandeepa.das_backend.constant.AppointmentStatus.CONFIRMED)
          and (:start < a.endTime and :end > a.startTime)
    """)
    boolean existsOverlap(@Param("doctorId") Long doctorId,
                          @Param("start") LocalDateTime start,
                          @Param("end") LocalDateTime end);

    Page<AppointmentEntity> findByPatient_Id(Long patientId, Pageable p);
    Page<AppointmentEntity> findByDoctor_Id(Long doctorId, Pageable p);
}
