package com.mandeepa.das_backend.service.prescription;

import com.mandeepa.das_backend.constant.AppointmentStatus;
import com.mandeepa.das_backend.dto.prescription.*;
import com.mandeepa.das_backend.entity.PrescriptionEntity;
import com.mandeepa.das_backend.exception.CompletedStatusNotFoundException;
import com.mandeepa.das_backend.exception.DuplicateFoundException;
import com.mandeepa.das_backend.exception.ResourceNotFoundException;
import com.mandeepa.das_backend.exception.UnAuthorizedException;
import com.mandeepa.das_backend.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
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
        log.info("Creating prescription for appointmentId: {} by doctor: {}", appointmentId, doctorUsername);

        var doctorUser = userRepository.findByUsername(doctorUsername)
                .orElseThrow(() -> {
                    log.error("User not found: {}", doctorUsername);
                    return new ResourceNotFoundException("User not found");
                });

        var doctor = doctorRepository.findByUserId(doctorUser.getId())
                .orElseThrow(() -> {
                    log.error("Doctor not found for userId: {}", doctorUser.getId());
                    return new ResourceNotFoundException("Doctor not found");
                });

        var appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> {
                    log.error("Appointment not found with id: {}", appointmentId);
                    return new ResourceNotFoundException("Appointment not found");
                });

        if (!appointment.getDoctor().getId().equals(doctor.getId())) {
            log.warn("Unauthorized prescription attempt by doctor: {} for appointment: {}", doctorUsername, appointmentId);
            throw new UnAuthorizedException("Not your appointment");
        }

        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            log.warn("Prescription creation attempted before appointment COMPLETED: {}", appointmentId);
            throw new CompletedStatusNotFoundException("Prescription allowed only after COMPLETED");
        }

        if (prescriptionRepository.existsByAppointment_Id(appointmentId)) {
            log.warn("Prescription already exists for appointmentId: {}", appointmentId);
            throw new DuplicateFoundException("Prescription already exists");
        }

        var prescription = prescriptionRepository.save(PrescriptionEntity.builder()
                .appointment(appointment)
                .diagnosis(request.getDiagnosis())
                .advice(request.getAdvice())
                .build());

        log.info("Prescription created successfully with id: {} for appointmentId: {}", prescription.getId(), appointmentId);
        return new PrescriptionResponse(prescription.getId(), appointment.getId(), prescription.getDiagnosis(), prescription.getAdvice(), prescription.getCreatedAt().toString());
    }

    @Override
    public PrescriptionResponse getPrescription(Long appointmentId, String username) {
        log.info("Fetching prescription for appointmentId: {} requested by user: {}", appointmentId, username);

        var prescription = prescriptionRepository.findByAppointment_Id(appointmentId)
                .orElseThrow(() -> {
                    log.error("Prescription not found for appointmentId: {}", appointmentId);
                    return new ResourceNotFoundException("Prescription not found");
                });

        log.info("Prescription retrieved successfully for appointmentId: {}", appointmentId);
        // ownership/doctor/admin checks are ensured in controller via roles and additional checks in appointment getPrescription
        return new PrescriptionResponse(prescription.getId(), appointmentId, prescription.getDiagnosis(), prescription.getAdvice(), prescription.getCreatedAt().toString());
    }
}
