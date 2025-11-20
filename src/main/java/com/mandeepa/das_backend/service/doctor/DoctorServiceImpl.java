package com.mandeepa.das_backend.service.doctor;

import com.mandeepa.das_backend.constant.UserType;
import com.mandeepa.das_backend.dto.doctor.*;
import com.mandeepa.das_backend.entity.*;
import com.mandeepa.das_backend.exception.DuplicateFoundException;
import com.mandeepa.das_backend.exception.ResourceNotFoundException;
import com.mandeepa.das_backend.exception.UnAuthorizedException;
import com.mandeepa.das_backend.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final SpecializationRepository specializationRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override @Transactional
    public DoctorPublicResponse createDoctor(DoctorCreateRequest request) {
        log.info("Creating doctor with email: {}", request.getEmail());
        if (userRepository.findByUsername(request.getEmail()).isPresent()) {
            log.warn("Duplicate email found: {}", request.getEmail());
            throw new DuplicateFoundException("Email already used");
        }

        if (doctorRepository.existsByRegNo(request.getRegNo())) {
            log.warn("Duplicate registration number found: {}", request.getRegNo());
            throw new DuplicateFoundException("regNo already used");
        }

        var spec = specializationRepository.findById(request.getSpecializationId())
                .orElseThrow(() -> {
                    log.error("Specialization not found with id: {}", request.getSpecializationId());
                    return new ResourceNotFoundException("Specialization not found");
                });

        var user = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .userType(UserType.DOCTOR)
                .build();
        userRepository.save(user);
        log.info("User created for doctor: {}", user.getUsername());

        var doctor = DoctorEntity.builder()
                .user(user)
                .regNo(request.getRegNo())
                .specialization(spec)
                .yearsOfExp(request.getYearsOfExp())
                .bio(request.getBio())
                .build();
        doctorRepository.save(doctor);
        log.info("Doctor created successfully with ID: {}", doctor.getId());
        return toPublic(doctor);
    }

    private DoctorPublicResponse toPublic(DoctorEntity d) {
        String fullName = (d.getUser().getFirstName() + " " + d.getUser().getLastName()).trim();
        return DoctorPublicResponse.builder()
                .id(d.getId())
                .fullName(fullName)
                .regNo(d.getRegNo())
                .specialization(d.getSpecialization().getName())
                .yearsOfExp(d.getYearsOfExp())
                .bio(d.getBio())
                .build();
    }

    @Override
    public Page<DoctorPublicResponse> getDoctorList(String name, Long specId, Pageable pageable) {
        log.info("Fetching doctor list with name filter: '{}' and specializationId: {}", name, specId);
//        Pageable p = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        String q = name == null ? "" : name;
        Page<DoctorEntity> res = (specId == null)
                ? doctorRepository.findByUser_FirstNameContainingIgnoreCaseOrUser_LastNameContainingIgnoreCase(q, q, pageable)
                : doctorRepository.findBySpecialization_IdAndUser_FirstNameContainingIgnoreCaseOrSpecialization_IdAndUser_LastNameContainingIgnoreCase(specId, q, specId, q, pageable);
        log.info("Fetched {} doctors", res.getTotalElements());
        return res.map(this::toPublic);
    }

    @Override
    public DoctorPublicResponse getDoctorById(Long id) {
        log.info("Fetching doctor by ID: {}", id);
        var doctor = doctorRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Doctor not found with ID: {}", id);
                    return new ResourceNotFoundException("Doctor not found");
                });
        log.info("Doctor found: {}", doctor.getUser().getUsername());
        return toPublic(doctor);
    }

    @Override
    @Transactional
    public DoctorPublicResponse updateDoctor(Long id, DoctorUpdateRequest request, String actorUsername) {
        log.info("Updating doctor with ID: {} by user: {}", id, actorUsername);

        var doctor = doctorRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Doctor not found with ID: {}", id);
                    return new ResourceNotFoundException("Doctor not found");
                });

        var actor = userRepository.findByUsername(actorUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        boolean isAdmin = actor.getUserType() == UserType.ADMIN;
        boolean isSelf = doctor.getUser().getId().equals(actor.getId());
        if (!isAdmin && !isSelf) {
            log.warn("Unauthorized update attempt by user: {} on doctor ID: {}", actorUsername, id);
            throw new UnAuthorizedException("Not allowed");
        }

        var spec = specializationRepository.findById(request.getSpecializationId())
                .orElseThrow(() -> {
                    log.error("Specialization not found with ID: {}", request.getSpecializationId());
                    return new ResourceNotFoundException("Specialization not found");
                });

        doctor.getUser().setFirstName(request.getFirstName());
        doctor.getUser().setLastName(request.getLastName());
        doctor.setYearsOfExp(request.getYearsOfExp());
        doctor.setBio(request.getBio());
        doctor.setSpecialization(spec);
        log.info("Doctor updated successfully: {}", doctor.getId());
        return toPublic(doctor);
    }
}
