package com.mandeepa.das_backend.service.prescription;

import com.mandeepa.das_backend.constant.AppointmentStatus;
import com.mandeepa.das_backend.dto.prescription.*;
import com.mandeepa.das_backend.entity.PrescriptionEntity;
import com.mandeepa.das_backend.exception.DuplicateFoundException;
import com.mandeepa.das_backend.exception.ResourceNotFoundException;
import com.mandeepa.das_backend.exception.UnAuthorizedException;
import com.mandeepa.das_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository repo;
    private final AppointmentRepository apptRepo;
    private final UserRepository userRepo;
    private final DoctorRepository doctorRepo;

    @Override
    @Transactional
    public PrescriptionResponse create(Long appointmentId, String doctorUsername, PrescriptionCreateRequest req) {
        var doctorUser = userRepo.findByUsername(doctorUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        var doctor = doctorRepo.findByUserId(doctorUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        var appt = apptRepo.findById(appointmentId).
                orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
        if (!appt.getDoctor().getId().equals(doctor.getId())) throw new UnAuthorizedException("Not your appointment");

        if (appt.getStatus() != AppointmentStatus.COMPLETED) {
            throw new DuplicateFoundException("Prescription allowed only after COMPLETED");
        }

        if (repo.existsByAppointment_Id(appointmentId)) throw new DuplicateFoundException("Prescription already exists");

        var pres = repo.save(PrescriptionEntity.builder()
                .appointment(appt)
                .diagnosis(req.getDiagnosis())
                .advice(req.getAdvice())
                .build());
        return new PrescriptionResponse(pres.getId(), appt.getId(), pres.getDiagnosis(), pres.getAdvice(), pres.getCreatedAt().toString());
    }

    @Override
    public PrescriptionResponse get(Long appointmentId, String username) {
        var pres = repo.findByAppointment_Id(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found"));

        // ownership/doctor/admin checks are ensured in controller via roles + additional checks in appointment get
        return new PrescriptionResponse(pres.getId(), appointmentId, pres.getDiagnosis(), pres.getAdvice(), pres.getCreatedAt().toString());
    }
}
