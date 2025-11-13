package com.mandeepa.das_backend.service.specialization;

import com.mandeepa.das_backend.dto.specialization.SpecializationCreateRequest;
import com.mandeepa.das_backend.dto.specialization.SpecializationResponse;
import com.mandeepa.das_backend.dto.specialization.SpecializationUpdateRequest;
import com.mandeepa.das_backend.entity.SpecializationEntity;
import com.mandeepa.das_backend.exception.DuplicateFoundException;
import com.mandeepa.das_backend.exception.ResourceNotFoundException;
import com.mandeepa.das_backend.repository.DoctorRepository;
import com.mandeepa.das_backend.repository.SpecializationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecializationServiceImpl implements SpecializationService {

    private final SpecializationRepository specializationRepository;
    private final DoctorRepository doctorRepository;


    @Override
    @Transactional
    public SpecializationResponse createSpecialization(SpecializationCreateRequest request) {
        if (specializationRepository.existsByNameIgnoreCase(request.getName())) {
            throw new DuplicateFoundException("Specialization exists");
        }
        var specialization = specializationRepository.save(SpecializationEntity.builder().name(request.getName().trim()).build());
        return new SpecializationResponse(specialization.getId(), specialization.getName());
    }

    @Override
    public List<SpecializationResponse> getAllSpecialization() {
        return specializationRepository.findAll().stream()
                .map(specializationEntity -> new SpecializationResponse(specializationEntity.getId(),
                        specializationEntity.getName()))
                .toList();
    }

    @Override
    @Transactional
    public SpecializationResponse updateSpecialization(Long id, SpecializationUpdateRequest request) {
        var specialization = specializationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialization not found"));

        specialization.setName(request.getName().trim());
        return new SpecializationResponse(specialization.getId(), specialization.getName());
    }

    @Override
    @Transactional
    public void deleteSpecialization(Long id) {
        var count = doctorRepository.countBySpecialization_Id(id);
        if (count > 0) {
            throw new DuplicateFoundException("Cannot delete specialization in use");
        }
        if (!specializationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Specialization not found");
        }
        specializationRepository.deleteById(id);
    }
}
