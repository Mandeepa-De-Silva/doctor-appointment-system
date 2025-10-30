package com.mandeepa.das_backend.repository;

import com.mandeepa.das_backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByUsername(String username);
}
