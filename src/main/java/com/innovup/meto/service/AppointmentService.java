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
import com.innovup.meto.repository.AppointmentRepository;
import com.innovup.meto.repository.ChirurgieRepo;
import com.innovup.meto.repository.UserRepository;
import com.innovup.meto.request.AppointmentRequest;
import com.innovup.meto.result.AppointmentResult;
import com.innovup.meto.result.AppointmentStatsResult;
import com.innovup.meto.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ChirurgieRepo surgeryRepository;
    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final AppointmentMapper appointmentMapper;

    public List<AppointmentResult> findAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(appointmentMapper::entityToResult)
                .toList();
    }

    public List<AppointmentResult> findAllAppointmentsByDoctorIdNull() {
        return appointmentRepository.findByDoctorIsNull()
                .stream()
                .collect(Collectors.mapping(appointmentMapper::entityToResult, Collectors.toList()));
    }

    public List<AppointmentResult> findAllInProgressAppointmentsByDoctor(UUID doctorId) {
        return appointmentRepository.findByDoctorIdAndStatusOrderByCreatedOn(doctorId ,AppointmentStatus.IN_PROGRESS).stream()
                .map(appointments -> appointments.stream()
                        .map(appointmentMapper::entityToResult)
                        .collect(Collectors.toList()))
                .findFirst()
                .orElse(Collections.emptyList());
    }

    public List<AppointmentResult> findAllAccptedAppointmentsByDoctor(UUID doctorId) {
        return appointmentRepository.findByDoctorIdAndStatusOrderByCreatedOn(doctorId ,AppointmentStatus.ACCEPTED).stream()
                .map(appointments -> appointments.stream()
                        .map(appointmentMapper::entityToResult)
                        .collect(Collectors.toList()))
                .findFirst()
                .orElse(Collections.emptyList());
    }
    public List<AppointmentResult> findAllAppointmentsByPatient(UUID patientId) {
        return appointmentRepository.findAppointmentByPatientIdOrderByCreatedOn(patientId).stream()
                .map(appointments -> appointments.stream()
                        .map(appointmentMapper::entityToResult)
                        .collect(Collectors.toList()))
                .findFirst()
                .orElse(Collections.emptyList());
    }

    public Optional<AppointmentResult> findAppointmentById(UUID id) {
        return   appointmentRepository.findById(id)
                .map(appointmentMapper::entityToResult);
    }
    public AppointmentResult createAppointment(AppointmentRequest request) {
        var surgery = surgeryRepository.findById(request.getSurgeryId()).orElseThrow(SurgeryNotFoundException::new);
       // var patient = userRepository.findById(request.getPatient().getId()).orElseThrow(() -> new UserNotFoundException(Role.PATIENT));
      //  patient.setGender(request.getPatient().getGender());
     //   patient.setWeight(request.getPatient().getWeight());

        var appointment = Appointment.builder()
                .withId(UUID.randomUUID())
                .withDescription(request.getNote())
                .withStatus(request.getDoctorId() != null ? AppointmentStatus.IN_PROGRESS : AppointmentStatus.CREATED)
                .withCreatedOn(LocalDateTime.now())
                .withSurgery(surgery)
                .withAge(request.getAge())
                .withImage(request.getImage())
                .withPhone(request.getPhone())
                .withTypeSang(request.getTypeSang())
                .withVille(request.getVille())
                .withWeight(request.getWeight())
                .withDateRDV(request.getDateRDV())
                .withPatient(request.getPatientId()!= null ? userRepository.findById(request.getPatientId()).orElseThrow(() -> new UserNotFoundException(Role.PATIENT)) : null
                )
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
                                .withDateRDV(request.getDateRDV())
                                .build()
                )
                .build();
        return appointmentMapper.entityToResult(appointmentRepository.save(appointment));
    }


   /** public AppointmentResult updateAppointment(UUID appointmentId, UpdateAppointmentRequest request) {
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
            rendezVous.setDateRDV(request.getRendezVous());
            rendezVous.setStatus(RendezVousStatus.UPDATED);
            rendezVous.setLastUpdatedOn(LocalDateTime.now());
            appointment.setRendezVous(rendezVous);
        }
        appointment.setLastUpdatedBy(admin);
        appointment.setLastUpdatedOn(LocalDateTime.now());
        return appointmentMapper.entityToResult(appointmentRepository.save(appointment));
    }**/
   public AppointmentResult updateAppointment(UUID appointmentId, UUID doctorId) {
       var appointment = appointmentRepository.findById(appointmentId).orElseThrow(AppointmentNotFoundException::new);

       if (doctorId != null) {
           var doctor = userRepository.findById(doctorId).orElseThrow(() -> new UserNotFoundException(Role.DOCTOR));
           appointment.setDoctor(doctor);
       }
       appointment.setStatus(AppointmentStatus.IN_PROGRESS);


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
        appointment.setLastUpdatedOn(LocalDateTime.now());
        return appointmentMapper.entityToResult(appointmentRepository.save(appointment));
    }
    public AppointmentResult acceptAppointment(UUID appointmentId)
            throws AppointmentNotFoundException, UserNotFoundException {
        var appointment = appointmentRepository.findById(appointmentId).orElseThrow(AppointmentNotFoundException::new);
        appointment.setStatus(AppointmentStatus.ACCEPTED);
        appointment.setLastUpdatedOn(LocalDateTime.now());

        return appointmentMapper.entityToResult(appointmentRepository.save(appointment));
    }

    public AppointmentResult rejectAppointment(UUID appointmentId)
            throws AppointmentNotFoundException, UserNotFoundException {
        var appointment = appointmentRepository.findById(appointmentId).orElseThrow(AppointmentNotFoundException::new);
        appointment.setStatus(AppointmentStatus.REJECTED);
        appointment.setLastUpdatedOn(LocalDateTime.now());

        return appointmentMapper.entityToResult(appointmentRepository.save(appointment));
    }



        public List<Object[]> findMostFrequentSurgeryWithNameAndImageAndCount() {
            return appointmentRepository.findMostFrequentSurgeryWithNameAndImageAndCount();
        }

    public List<Object[]> findMostFrequentUserWithNameAndImageAndCount() {
        return appointmentRepository.findMostFrequentUserWithNameAndImageAndCount();
    }
    public List<Object[]> getMostFrequentCity() {
        return appointmentRepository.getMostFrequentCity();
    }

    public AppointmentStatsResult findAppointmentStatsByDoctor(UUID doctorId) {
        Tuple result = appointmentRepository.findAppointmentStatsByDoctorId(doctorId);
        if (result != null) {
            AppointmentStatsResult statsResult = new AppointmentStatsResult();
            statsResult.setTotal(result.get("total", Long.class));
            statsResult.setMales(result.get("males", Long.class));
            statsResult.setFemales(result.get("females", Long.class));
            return statsResult;
        } else {
            // Return appropriate response when no data found
            return null;
        }
    }


    public List<Object[]> getAppointmentsCountByMonthAndYear() {
        return appointmentRepository.getAppointmentsCountByMonthAndYear();
    }

}
