package com.mandeepa.das_backend.service.appointment;

import com.mandeepa.das_backend.constant.AppointmentStatus;
import com.mandeepa.das_backend.constant.UserType;
import com.mandeepa.das_backend.dto.appointment.*;
import com.mandeepa.das_backend.entity.*;
import com.mandeepa.das_backend.exception.DuplicateFoundException;
import com.mandeepa.das_backend.exception.ResourceNotFoundException;
import com.mandeepa.das_backend.exception.UnAuthorizedException;
import com.mandeepa.das_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    private AppointmentResponse toResp(AppointmentEntity a) {

        return AppointmentResponse.builder()
                .id(a.getId())
                .doctorId(a.getDoctor().getId())
                .patientId(a.getPatient().getId())
                .startTime(a.getStartTime().toString())
                .endTime(a.getEndTime().toString())
                .status(a.getStatus())
                .notes(a.getNotes())
                .build();
    }

    @Override
    @Transactional
    public AppointmentResponse create(String patientUsername, AppointmentCreateRequest req) {
        var user = userRepository.findByUsername(patientUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getUserType() != UserType.PATIENT) {
            throw new UnAuthorizedException("Only patients can book");
        }

        var patient = patientRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        var doctor = doctorRepository.findById(req.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        OffsetDateTime start = OffsetDateTime.parse(req.getStartTime());
        OffsetDateTime end = OffsetDateTime.parse(req.getEndTime());

        if (!end.isAfter(start)) {
            throw new DuplicateFoundException("endTime must be after startTime");
        }

        if (!start.isAfter(OffsetDateTime.now())) {
            throw new DuplicateFoundException("startTime must be in the future");
        }

        if (appointmentRepository.existsOverlap(doctor.getId(), start, end)) {
            throw new DuplicateFoundException("Overlapping appointment");
        }

        var appointment = appointmentRepository.save(AppointmentEntity.builder()
                .patient(patient).doctor(doctor)
                .startTime(start).endTime(end)
                .status(AppointmentStatus.PENDING)
                .notes(req.getNotes())
                .build());
        return toResp(appointment);
    }

    @Override
    public Page<AppointmentResponse> listMine(String username, int page, int size) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Pageable pageable = PageRequest.of(page, size, Sort.by("startTime").descending());

        if (user.getUserType() == UserType.PATIENT) {
            var patient = patientRepository.findByUser(user)
                    .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

            return appointmentRepository.findByPatient_Id(patient.getId(), pageable).map(this::toResp);
        } else if (user.getUserType() == UserType.DOCTOR) {
            var doctor = doctorRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

            return appointmentRepository.findByDoctor_Id(doctor.getId(), pageable).map(this::toResp);
        } else {
            return Page.empty();
        }
    }

    @Override
    @Transactional
    public AppointmentResponse cancel(Long id, String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        var appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        boolean isOwnerPatient = appointment.getPatient().getUser().getId().equals(user.getId());
        boolean isOwnerDoctor = appointment.getDoctor().getUser().getId().equals(user.getId());
        boolean allowed = isOwnerPatient || isOwnerDoctor || user.getUserType() == UserType.ADMIN;

        if (!allowed) {
            throw new UnAuthorizedException("Not allowed");
        }

        if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
            throw new DuplicateFoundException("Cannot cancel completed");
        }
        appointment.setStatus(AppointmentStatus.CANCELLED);
        return toResp(appointment);
    }

    @Override
    @Transactional
    public AppointmentResponse updateStatus(Long id, String doctorUsername, String status) {
        var doctorUser = userRepository.findByUsername(doctorUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (doctorUser.getUserType() != UserType.DOCTOR) {
            throw new UnAuthorizedException("Only doctors can update status");
        }

        var doctor = doctorRepository.findByUserId(doctorUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        var appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        if (!appointment.getDoctor().getId().equals(doctor.getId())) {
            throw new UnAuthorizedException("Not your appointment");
        }

        var newStatus = AppointmentStatus.valueOf(status);

        switch (newStatus) {
            case CONFIRMED, REJECTED -> {
                if (appointment.getStatus() != AppointmentStatus.PENDING) {
                    throw new DuplicateFoundException("Invalid transition");
                }
            }
            case COMPLETED -> {
                if (appointment.getStatus() != AppointmentStatus.CONFIRMED) {
                    throw new DuplicateFoundException("Invalid transition");
                }
            }
            case CANCELLED -> {} // permitted but typically patient/doctor cancel handled in cancel()
            default -> {}
        }
        appointment.setStatus(newStatus);
        return toResp(appointment);
    }

    @Override
    public AppointmentResponse getById(Long id, String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        var appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        boolean allowed = appointment.getPatient().getUser().getId().equals(user.getId())
                || appointment.getDoctor().getUser().getId().equals(user.getId())
                || user.getUserType() == UserType.ADMIN;

        if (!allowed) {
            throw new UnAuthorizedException("Not allowed");
        }
        return toResp(appointment);
    }
}
