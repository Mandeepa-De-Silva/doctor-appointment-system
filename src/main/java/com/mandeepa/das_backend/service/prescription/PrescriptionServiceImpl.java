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

    private final PrescriptionRepository prescriptionRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    @Override
    @Transactional
    public PrescriptionResponse createPrescription(Long appointmentId, String doctorUsername, PrescriptionCreateRequest request) {
        var doctorUser = userRepository.findByUsername(doctorUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        var doctor = doctorRepository.findByUserId(doctorUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        var appt = appointmentRepository.findById(appointmentId).
                orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
        if (!appt.getDoctor().getId().equals(doctor.getId())) throw new UnAuthorizedException("Not your appointment");

        if (appt.getStatus() != AppointmentStatus.COMPLETED) {
            throw new DuplicateFoundException("Prescription allowed only after COMPLETED");
        }

        if (prescriptionRepository.existsByAppointment_Id(appointmentId)) throw new DuplicateFoundException("Prescription already exists");

        var pres = prescriptionRepository.save(PrescriptionEntity.builder()
                .appointment(appt)
                .diagnosis(request.getDiagnosis())
                .advice(request.getAdvice())
                .build());
        return new PrescriptionResponse(pres.getId(), appt.getId(), pres.getDiagnosis(), pres.getAdvice(), pres.getCreatedAt().toString());
    }

    @Override
    public PrescriptionResponse getPrescription(Long appointmentId, String username) {
        var pres = prescriptionRepository.findByAppointment_Id(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found"));

        // ownership/doctor/admin checks are ensured in controller via roles and additional checks in appointment getPrescription
        return new PrescriptionResponse(pres.getId(), appointmentId, pres.getDiagnosis(), pres.getAdvice(), pres.getCreatedAt().toString());
    }
}
