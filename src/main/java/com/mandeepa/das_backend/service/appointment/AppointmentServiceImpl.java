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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
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
    public AppointmentResponse createAppointment(String patientUsername, AppointmentCreateRequest req) {

        log.info("Creating appointment: user={}, doctorId={}, start={}, end={}",
                patientUsername, req.getDoctorId(), req.getStartTime(), req.getEndTime());

        var user = userRepository.findByUsername(patientUsername)
                .orElseThrow(() -> {
                    log.error("User not found: {}", patientUsername);
                    return new ResourceNotFoundException("User not found");
                });

        if (user.getUserType() != UserType.PATIENT) {
            log.warn("Unauthorized appointment creation attempt by user={} type={}",
                    patientUsername, user.getUserType());
            throw new UnAuthorizedException("Only patients can book");
        }

        var patient = patientRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        var doctor = doctorRepository.findById(req.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        LocalDateTime start = req.getStartTime();
        LocalDateTime end = req.getEndTime();

        if (!end.isAfter(start)) {
            log.warn("Invalid appointment time range: endTime <= startTime");
            throw new DuplicateFoundException("endTime must be after startTime");
        }

        if (!start.isAfter(LocalDateTime.now())) {
            log.warn("Appointment start time must be in the future: {}", start);
            throw new DuplicateFoundException("startTime must be in the future");
        }

        if (appointmentRepository.existsOverlap(doctor.getId(), start, end)) {
            log.warn("Appointment overlap detected for doctorId={} start={} end={}",
                    doctor.getId(), start, end);
            throw new DuplicateFoundException("Overlapping appointment");
        }

        var appointment = appointmentRepository.save(AppointmentEntity.builder()
                .patient(patient).doctor(doctor)
                .startTime(start).endTime(end)
                .status(AppointmentStatus.PENDING)
                .notes(req.getNotes())
                .build());
        log.info("Appointment created successfully: appointmentId={}", appointment.getId());
        return toResp(appointment);
    }

    @Override
    public Page<AppointmentResponse> getAppointmentByRole(String username, int page, int size) {
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
    public AppointmentResponse cancelAppointment(Long id, String username) {
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
    public AppointmentResponse updateAppointmentStatus(Long id, String doctorUsername, String status) {
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
    public AppointmentResponse getAppointmentById(Long id, String username) {
        log.info("Fetching appointment by ID: appointmentId={}, username={}", id, username);
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        var appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        boolean allowed = appointment.getPatient().getUser().getId().equals(user.getId())
                || appointment.getDoctor().getUser().getId().equals(user.getId())
                || user.getUserType() == UserType.ADMIN;

        if (!allowed) {
            log.warn("Unauthorized appointment access attempt by user={} appointmentId={}", username, id);
            throw new UnAuthorizedException("Not allowed");
        }
        log.info("Appointment fetched successfully: id={}", id);
        return toResp(appointment);
    }
}
