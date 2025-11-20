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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpecializationServiceImpl implements SpecializationService {

    private final SpecializationRepository specializationRepository;
    private final DoctorRepository doctorRepository;

    @Override
    @Transactional
    public SpecializationResponse createSpecialization(SpecializationCreateRequest request) {
        log.info("Creating specialization with name: {}", request.getName());

        if (specializationRepository.existsByNameIgnoreCase(request.getName())) {
            log.warn("Specialization already exists: {}", request.getName());
            throw new DuplicateFoundException("Specialization exists");
        }

        var specialization = specializationRepository
                .save(SpecializationEntity.builder().name(request.getName().trim()).build());

        log.info("Specialization created successfully with ID: {}", specialization.getId());
        return new SpecializationResponse(specialization.getId(), specialization.getName());
    }

    @Override
    public List<SpecializationResponse> getAllSpecialization() {
        log.info("Fetching all specializations");

        var list = specializationRepository.findAll().stream()
                .map(specializationEntity -> new SpecializationResponse(specializationEntity.getId(),
                        specializationEntity.getName()))
                .toList();
        log.info("Fetched {} specializations", list.size());
        return list;

    }

    @Override
    @Transactional
    public SpecializationResponse updateSpecialization(Long id, SpecializationUpdateRequest request) {
        log.info("Updating specialization with ID: {}", id);

        var specialization = specializationRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Specialization not found with ID: {}", id);
                    return new ResourceNotFoundException("Specialization not found");
                });

        specialization.setName(request.getName().trim());
        log.info("Specialization updated successfully with ID: {}", id);
        return new SpecializationResponse(specialization.getId(), specialization.getName());
    }

    @Override
    @Transactional
    public void deleteSpecialization(Long id) {
        log.info("Deleting specialization with ID: {}", id);

        var count = doctorRepository.countBySpecialization_Id(id);
        if (count > 0) {
            log.warn("Cannot delete specialization ID {}: used by {} doctors", id, count);
            throw new DuplicateFoundException("Cannot delete specialization in use");
        }
        if (!specializationRepository.existsById(id)) {
            log.error("Specialization not found with ID: {}", id);
            throw new ResourceNotFoundException("Specialization not found");
        }
        specializationRepository.deleteById(id);
        log.info("Specialization deleted successfully with ID: {}", id);
    }
}
