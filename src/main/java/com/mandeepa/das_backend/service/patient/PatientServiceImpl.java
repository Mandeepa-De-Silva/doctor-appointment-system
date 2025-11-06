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

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final UserRepository userRepo;
    private final PatientRepository patientRepo;

    @Override
    public PatientMeResponse me(String username) {
        UserEntity user = userRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        PatientEntity patient = patientRepo.findByUser(user)
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
    public PatientMeResponse updateMe(String username, PatientUpdateRequest req) {
        UserEntity user = userRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        PatientEntity patient = patientRepo.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Patient profile not found"));

        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setAddress(req.getAddress());
        patient.setPhone(req.getPhone());
        patient.setDob(req.getDob());
        return me(username);
    }
}
