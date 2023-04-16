package com.innovup.meto.service;

import com.innovup.meto.entity.Appointment;
import com.innovup.meto.entity.RendezVous;
import com.innovup.meto.enums.AppointmentStatus;
import com.innovup.meto.enums.RendezVousStatus;
import com.innovup.meto.enums.Role;
import com.innovup.meto.exception.AppointmentNotFoundException;
import com.innovup.meto.exception.SurgeryNotFoundException;
import com.innovup.meto.exception.UserNotFoundException;
import com.innovup.meto.mapper.AppointmentMapper;
import com.innovup.meto.pojo.Administrator;
import com.innovup.meto.repository.AppointmentRepository;
import com.innovup.meto.repository.SurgeryRepository;
import com.innovup.meto.repository.UserRepository;
import com.innovup.meto.request.AppointmentRequest;
import com.innovup.meto.request.UpdateAppointmentRequest;
import com.innovup.meto.result.AppointmentResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final SurgeryRepository surgeryRepository;
    private final UserRepository userRepository;
    private final AppointmentMapper appointmentMapper;

    public List<AppointmentResult> findAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(appointmentMapper::entityToResult)
                .toList();
    }

    public List<AppointmentResult> findAllAppointmentsByDoctor(UUID doctorId) {
        return appointmentRepository.findAppointmentsByDoctorIdOrderByCreatedOn(doctorId).stream()
                .map(appointmentMapper::entityToResult)
                .toList();
    }

    public AppointmentResult createAppointment(AppointmentRequest request) {
        var surgery = surgeryRepository.findById(request.getSurgeryId()).orElseThrow(SurgeryNotFoundException::new);
        var patient = userRepository.findById(request.getPatient().getId()).orElseThrow(() -> new UserNotFoundException(Role.PATIENT));

        patient.setGender(request.getPatient().getGender());
        patient.setWeight(request.getPatient().getWeight());

        var appointment = Appointment.builder()
                .withId(UUID.randomUUID())
                .withNote(request.getNote())
                .withStatus(request.getDoctorId() != null ? AppointmentStatus.IN_PROGRESS : AppointmentStatus.CREATED)
                .withCreatedOn(LocalDateTime.now())
                .withSurgery(surgery)
                .withPatient(patient)
                .withDoctor(
                        request.getDoctorId() != null ? userRepository.findById(request.getDoctorId()).orElseThrow(() -> new UserNotFoundException(Role.DOCTOR)) : null
                )
                // Appointment not validated by administrator yet
                .withAdministrator(null)
                .withRendezVous(
                        RendezVous.builder()
                                .withId(UUID.randomUUID())
                                .withStatus(RendezVousStatus.CREATED)
                                .withCreatedOn(LocalDateTime.now())
                                .withDate(request.getRendezVous())
                                .build()
                )
                .build();
        return appointmentMapper.entityToResult(appointmentRepository.save(appointment));
    }

    public AppointmentResult updateAppointment(UUID appointmentId, UpdateAppointmentRequest request) {
        var appointment = appointmentRepository.findById(appointmentId).orElseThrow(AppointmentNotFoundException::new);
        var admin = userRepository.findById(request.getAdminId()).orElseThrow(() -> new UserNotFoundException(Role.ADMIN));
        if (request.getDoctorId() != null) {
            var doctor = userRepository.findById(request.getDoctorId()).orElseThrow(() -> new UserNotFoundException(Role.DOCTOR));
            appointment.setDoctor(doctor);
        }
        if (request.getSurgeryId() != null) {
            var surgery = surgeryRepository.findById(request.getSurgeryId()).orElseThrow(SurgeryNotFoundException::new);
            appointment.setSurgery(surgery);
        }
        if (request.getRendezVous() != null) {
            var rendezVous = appointment.getRendezVous();
            rendezVous.setDate(request.getRendezVous());
            rendezVous.setStatus(RendezVousStatus.UPDATED);
            rendezVous.setLastUpdatedOn(LocalDateTime.now());
            appointment.setRendezVous(rendezVous);
        }
        appointment.setLastUpdatedBy(admin);
        appointment.setLastUpdatedOn(LocalDateTime.now());
        return appointmentMapper.entityToResult(appointmentRepository.save(appointment));
    }

    public AppointmentResult validateAppointment(UUID adminId, UUID appointmentId, UUID doctorId)
            throws AppointmentNotFoundException, UserNotFoundException {

        var appointment = appointmentRepository.findById(appointmentId).orElseThrow(AppointmentNotFoundException::new);
        var admin = userRepository.findById(adminId).orElseThrow(() -> new UserNotFoundException(Role.ADMIN));
        if (doctorId != null) {
            var doctor = userRepository.findById(doctorId).orElseThrow(() -> new UserNotFoundException(Role.DOCTOR));
            appointment.setDoctor(doctor);
        }
        appointment.setAdministrator(admin);
        appointment.setStatus(AppointmentStatus.IN_PROGRESS);
        return appointmentMapper.entityToResult(appointmentRepository.save(appointment));
    }
}
