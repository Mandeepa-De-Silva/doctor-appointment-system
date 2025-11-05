package com.mandeepa.das_backend.repository;

import com.mandeepa.das_backend.entity.SpecializationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecializationRepository extends JpaRepository<SpecializationEntity, Long> {

    boolean existsByNameIgnoreCase(String name);
}