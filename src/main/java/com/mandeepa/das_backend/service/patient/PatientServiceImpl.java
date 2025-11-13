package com.mandeepa.das_backend.service.patient;

import com.mandeepa.das_backend.dto.patient.*;
import com.mandeepa.das_backend.entity.PatientEntity;
import com.mandeepa.das_backend.entity.UserEntity;
import com.mandeepa.das_backend.exception.ResourceNotFoundException;
import com.mandeepa.das_backend.repository.PatientRepository;
import com.mandeepa.das_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;

    @Override
    public PatientMeResponse getPatientDetails(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        PatientEntity patient = patientRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Patient profile not found"));

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
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        PatientEntity patient = patientRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Patient profile not found"));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setAddress(request.getAddress());
        user.setDob(request.getDob());
        user.setPhoneNumber(request.getPhone());
        user.setUpdatedAt(LocalDateTime.now());
        patient.setPhone(request.getPhone());
        patient.setDob(request.getDob());
        return getPatientDetails(username);
    }
}
