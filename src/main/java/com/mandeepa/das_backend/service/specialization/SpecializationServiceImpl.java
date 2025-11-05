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

    private final SpecializationRepository repo;
    private final DoctorRepository doctorRepo;


    @Override
    @Transactional
    public SpecializationResponse create(SpecializationCreateRequest req) {
        if (repo.existsByNameIgnoreCase(req.getName())) {
            throw new DuplicateFoundException("Specialization exists");
        }
        var specialization = repo.save(SpecializationEntity.builder().name(req.getName().trim()).build());
        return new SpecializationResponse(specialization.getId(), specialization.getName());
    }

    @Override
    public List<SpecializationResponse> list() {
        return repo.findAll().stream()
                .map(s -> new SpecializationResponse(s.getId(), s.getName()))
                .toList();
    }

    @Override
    @Transactional
    public SpecializationResponse update(Long id, SpecializationUpdateRequest req) {
        var specialization = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialization not found"));

        specialization.setName(req.getName().trim());
        return new SpecializationResponse(specialization.getId(), specialization.getName());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        var count = doctorRepo.countBySpecialization_Id(id);
        if (count > 0) {
            throw new DuplicateFoundException("Cannot delete specialization in use");
        }
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Specialization not found");
        }
        repo.deleteById(id);
    }
}
