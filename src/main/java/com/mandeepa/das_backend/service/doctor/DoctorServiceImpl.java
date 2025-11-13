package com.mandeepa.das_backend.service.doctor;

import com.mandeepa.das_backend.constant.UserType;
import com.mandeepa.das_backend.dto.doctor.*;
import com.mandeepa.das_backend.entity.*;
import com.mandeepa.das_backend.exception.DuplicateFoundException;
import com.mandeepa.das_backend.exception.ResourceNotFoundException;
import com.mandeepa.das_backend.exception.UnAuthorizedException;
import com.mandeepa.das_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final SpecializationRepository specializationRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override @Transactional
    public DoctorPublicResponse createDoctor(DoctorCreateRequest request) {
        if (userRepository.findByUsername(request.getEmail()).isPresent()) {
            throw new DuplicateFoundException("Email already used");
        }

        if (doctorRepository.existsByRegNo(request.getRegNo())) {
            throw new DuplicateFoundException("regNo already used");
        }

        var spec = specializationRepository.findById(request.getSpecializationId())
                .orElseThrow(() -> new ResourceNotFoundException("Specialization not found"));

        var user = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .userType(UserType.DOCTOR)
                .build();
        userRepository.save(user);

        var doctor = DoctorEntity.builder()
                .user(user)
                .regNo(request.getRegNo())
                .specialization(spec)
                .yearsOfExp(request.getYearsOfExp())
                .bio(request.getBio())
                .build();
        doctorRepository.save(doctor);
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
//        Pageable p = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        String q = name == null ? "" : name;
        Page<DoctorEntity> res = (specId == null)
                ? doctorRepository.findByUser_FirstNameContainingIgnoreCaseOrUser_LastNameContainingIgnoreCase(q, q, pageable)
                : doctorRepository.findBySpecialization_IdAndUser_FirstNameContainingIgnoreCaseOrSpecialization_IdAndUser_LastNameContainingIgnoreCase(specId, q, specId, q, pageable);
        return res.map(this::toPublic);
    }

    @Override
    public DoctorPublicResponse getDoctorById(Long id) {
        return toPublic(doctorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Doctor not found")));
    }

    @Override @Transactional
    public DoctorPublicResponse updateDoctor(Long id, DoctorUpdateRequest request, String actorUsername) {
        var doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        var actor = userRepository.findByUsername(actorUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        boolean isAdmin = actor.getUserType() == UserType.ADMIN;
        boolean isSelf = doctor.getUser().getId().equals(actor.getId());
        if (!isAdmin && !isSelf) {
            throw new UnAuthorizedException("Not allowed");
        }

        var spec = specializationRepository.findById(request.getSpecializationId())
                .orElseThrow(() -> new ResourceNotFoundException("Specialization not found"));

        doctor.getUser().setFirstName(request.getFirstName());
        doctor.getUser().setLastName(request.getLastName());
        doctor.setYearsOfExp(request.getYearsOfExp());
        doctor.setBio(request.getBio());
        doctor.setSpecialization(spec);
        return toPublic(doctor);
    }
}
