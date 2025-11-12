package com.mandeepa.das_backend.repository;

import com.mandeepa.das_backend.entity.DoctorEntity;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {

    boolean existsByRegNo(String regNo);

    Optional<DoctorEntity> findByUserId(Integer userId);

    Page<DoctorEntity> findByUser_FirstNameContainingIgnoreCaseOrUser_LastNameContainingIgnoreCase(String f, String l, Pageable p);

    Page<DoctorEntity> findBySpecialization_IdAndUser_FirstNameContainingIgnoreCaseOrSpecialization_IdAndUser_LastNameContainingIgnoreCase(Long id1, String f, Long id2, String l, Pageable p);

    long countBySpecialization_Id(Long specializationId);
}
