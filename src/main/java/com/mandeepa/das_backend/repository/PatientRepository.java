package com.mandeepa.das_backend.repository;

import com.mandeepa.das_backend.entity.PatientEntity;
import com.mandeepa.das_backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

    Optional<PatientEntity> findByUser(UserEntity user);

    Optional<PatientEntity> findByUser_Username(String username);
}
