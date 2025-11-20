package com.mandeepa.das_backend.service.patient;

import com.mandeepa.das_backend.dto.patient.*;
import com.mandeepa.das_backend.entity.PatientEntity;
import com.mandeepa.das_backend.entity.UserEntity;
import com.mandeepa.das_backend.exception.ResourceNotFoundException;
import com.mandeepa.das_backend.repository.PatientRepository;
import com.mandeepa.das_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;

    @Override
    public PatientMeResponse getPatientDetails(String username) {
        log.info("Fetching patient details for username: {}", username);

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User not found with username: {}", username);
                    return new ResourceNotFoundException("User not found");
                });

        PatientEntity patient = patientRepository.findByUser(user)
                .orElseThrow(() -> {
                    log.error("Patient profile not found for user: {}", username);
                    return new ResourceNotFoundException("Patient profile not found");
                });

        log.info("Patient details retrieved successfully for username: {}", username);
        return PatientMeResponse.builder()
                .id(patient.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .phone(patient.getPhone())
                .dob(patient.getDob())
                .address(user.getAddress())
                .build();
    }

    @Override
    @Transactional
    public PatientMeResponse updatePatient(String username, PatientUpdateRequest request) {
        log.info("Updating patient profile for username: {}", username);

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User not found with username: {}", username);
                    return new ResourceNotFoundException("User not found");
                });

        PatientEntity patient = patientRepository.findByUser(user)
                .orElseThrow(() -> {
                    log.error("Patient profile not found for user: {}", username);
                    return new ResourceNotFoundException("Patient profile not found");
                });

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setAddress(request.getAddress());
        user.setDob(request.getDob());
        user.setPhoneNumber(request.getPhone());
        user.setUpdatedAt(LocalDateTime.now());
        patient.setPhone(request.getPhone());
        patient.setDob(request.getDob());

        log.info("Patient profile updated successfully for username: {}", username);
        return getPatientDetails(username);
    }
}
