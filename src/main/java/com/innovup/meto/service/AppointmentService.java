package com.innovup.meto.service;

import com.innovup.meto.entity.Appointment;
import com.innovup.meto.enums.AppointmentStatus;
import com.innovup.meto.enums.Role;
import com.innovup.meto.exception.SurgeryNotFoundException;
import com.innovup.meto.exception.UnauthorizedUserException;
import com.innovup.meto.exception.UserNotFoundException;
import com.innovup.meto.repository.AppointmentRepository;
import com.innovup.meto.repository.SurgeryRepository;
import com.innovup.meto.repository.UserRepository;
import com.innovup.meto.request.AppointmentRequest;
import com.innovup.meto.security.service.CustomUserDetailsService;
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
    private final CustomUserDetailsService customUserDetailsService;

    public List<Appointment> findAllAppointments() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> findAllAppointmentsByDoctor(UUID doctorId) {
        return appointmentRepository.findAppointmentByDoctorOrderByCreatedOn(doctorId);
    }

    public Appointment createAppointment(AppointmentRequest request) {
        var optionalSurgery = surgeryRepository.findById(request.getSurgeryId());
        if (optionalSurgery.isEmpty()) {
            throw new SurgeryNotFoundException();
        }

        var optionalDoctor = userRepository.findById(request.getDoctorId());
        if (optionalDoctor.isEmpty()) {
            throw new UserNotFoundException(Role.DOCTOR);
        }

        var appointmentStatus = AppointmentStatus.IN_PROGRESS;
        if (request.getDoctorId() == null || String.valueOf(request.getDoctorId()).equals("")) {
            // appointment needs administrator validation
            appointmentStatus = AppointmentStatus.CREATED;
        }

        var currentUser = customUserDetailsService.getCurrentUser();
        if (currentUser == null) {
            throw new UnauthorizedUserException();
        }

        var appointment = Appointment.builder()
                .withId(UUID.randomUUID())
                .withNote(request.getNote())
                .withStatus(appointmentStatus)
                .withCreatedOn(LocalDateTime.now())
                .withSurgery(optionalSurgery.get())
                .withPatient(currentUser)
                .withDoctor(optionalDoctor.get())
                // Appointment not validated by administrator yet
                .withAdministrator(null)
                .build();
        return appointmentRepository.save(appointment);
    }
}
