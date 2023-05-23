package com.innovup.meto.service;

import com.innovup.meto.entity.Appointment;
import com.innovup.meto.entity.Devis;
import com.innovup.meto.entity.RendezVous;
import com.innovup.meto.entity.User;
import com.innovup.meto.enums.AppointmentStatus;
import com.innovup.meto.enums.DevisStatus;
import com.innovup.meto.enums.RendezVousStatus;
import com.innovup.meto.enums.Role;
import com.innovup.meto.exception.*;
import com.innovup.meto.mapper.AppointmentMapper;
import com.innovup.meto.mapper.DevisMapper;
import com.innovup.meto.repository.AppointmentRepository;
import com.innovup.meto.repository.ChirurgieRepo;
import com.innovup.meto.repository.UserRepository;
import com.innovup.meto.request.AppointmentRequest;
import com.innovup.meto.request.DevisRequest;
import com.innovup.meto.request.UpdateAppointmentPatient;
import com.innovup.meto.result.AppointmentResult;
import com.innovup.meto.result.AppointmentStatsResult;
import com.innovup.meto.result.DevisResult;
import com.innovup.meto.result.DoctorResult;
import com.innovup.meto.security.service.CustomUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorsService doctorsService;

    private final ChirurgieRepo surgeryRepository;
    private final UserRepository userRepository;
    private final AppointmentMapper appointmentMapper;
    private final DevisMapper devisMapper;
    private final CustomUserDetailsServiceImpl customUserDetailsService;


    public List<AppointmentResult> findAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(appointmentMapper::entityToResult)
                .toList();
    }

    public List<AppointmentResult> findAllAppointmentsByDoctorIdNull() {
        return appointmentRepository.findByDoctorIsNullAndStatus(AppointmentStatus.CREATED)
                .stream().map(appointmentMapper::entityToResult).collect(Collectors.toList());
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
        var surgery = surgeryRepository.findById(request.getSurgeryId())
                .orElseThrow(SurgeryNotFoundException::new);

        // Retrieve the appointments of the doctor with the given doctorId and specific statuses from the database.
        List<Appointment> appointments = appointmentRepository.findAppointmentByDoctorIdAndStatusIn(
                request.getDoctorId(),
                Arrays.asList(
                        AppointmentStatus.IN_PROGRESS,
                        AppointmentStatus.ACCEPTED,
                        AppointmentStatus.WITH_DEVIS,
                        AppointmentStatus.CREATED
                )
        );

        // Filter the appointments based on the dateRdv field to get only the appointments that have the same dateRdv as the request.getDateRDV().
        LocalDate requestedDate = request.getDateRDV().toLocalDate();
        List<Appointment> filteredAppointments = appointments.stream()
                .filter(a -> a.getDateRDV().toLocalDate().isEqual(requestedDate))
                .collect(Collectors.toList());

        if (!filteredAppointments.isEmpty()) {
            // If the filtered appointments list is not empty, it means that the doctor already has an appointment at the requested dateRdv.
            log.info("Date already in use.");
            throw new AppointmentAlreadyExistsException();
        }

        // Check if the doctor is available on the requested date
        boolean isAvailable = doctorsService.isDoctorAvailable(request.getDoctorId(), request.getDateRDV());
        if (!isAvailable) {
            log.info("Doctor is not available on the requested date.");
            throw new DoctorNotAvailableException("The doctor is not available at the requested date and time.");
        }
        var appointment = Appointment.builder()
                .withId(UUID.randomUUID())
                .withDescription(request.getNote())
                .withStatus(
                        Optional.ofNullable(request.getDoctorId())
                                .map(d -> AppointmentStatus.IN_PROGRESS)
                                .orElse(AppointmentStatus.CREATED)
                )

                .withCreatedOn(LocalDateTime.now())
                .withSurgery(surgery)
                .withAge(request.getAge())
                .withImage(request.getImage())
                .withPhone(request.getPhone())
                .withTypeSang(request.getTypeSang())
                .withVille(request.getVille())
                .withWeight(request.getWeight())
                .withDateRDV(request.getDateRDV())
                .withAlcoolique(request.getAlcoolique())
                .withFumee(request.getFumee())
                .withDiabete(request.getDiabete())
                .withTension(request.getTension())
                .withAnalyseDiabete(request.getAnalyseDiabete())
                .withAutreAnalyse(request.getAutreAnalyse())
                .withAnalyseAncienOperation(request.getAnalyseAncienOperation())
                .withAutreMaladie(request.getAutreMaladie())
                .withAnalyseAutreMaladie(request.getAnalyseAutreMaladie())
                .withAncienOperation(request.getAncienOperation())
                .withDesAutreMaladie(request.getDesAutreMaladie())
                .withMesureDiabete(request.getMesureDiabete())
                .withMesureTension(request.getMesureTension())
                .withNomAncienOperation(request.getNomAncienOperation())

                .withPatient(request.getPatientId() != null ? userRepository.findById(request.getPatientId()).orElseThrow(() -> new UserNotFoundException(Role.PATIENT)) : null
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

    public AppointmentResult updateAppointment(UUID appointmentId, UpdateAppointmentPatient request) {
        var appointment = appointmentRepository.findById(appointmentId).orElseThrow(AppointmentNotFoundException::new);
        var patient = userRepository.findById(request.getPatientId()).orElseThrow(() -> new UserNotFoundException(Role.PATIENT));

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
       appointment.setImage(request.getImage());
        appointment.setAge(request.getAge());
        appointment.setWeight(request.getWeight());
        appointment.setDateRDV(request.getDateRDV());
        appointment.setTypeSang(request.getTypeSang());
        appointment.setVille(request.getVille());
        appointment.setPhone(request.getPhone());
        appointment.setDescription(request.getNote());
        appointment.setLastUpdatedOn(LocalDateTime.now());
        appointment.setAlcoolique(request.getAlcoolique());
        appointment.setFumee(request.getFumee());
        appointment.setDiabete(request.getDiabete());
        appointment.setTension(request.getTension());
        appointment.setAnalyseDiabete(request.getAnalyseDiabete());
        appointment.setAutreAnalyse(request.getAutreAnalyse());
        appointment.setAnalyseAncienOperation(request.getAnalyseAncienOperation());
        appointment.setAutreMaladie(request.getAutreMaladie());
        appointment.setAnalyseAutreMaladie(request.getAnalyseAutreMaladie());
        appointment.setAncienOperation(request.getAncienOperation());
        appointment.setDesAutreMaladie(request.getDesAutreMaladie());
        appointment.setMesureDiabete(request.getMesureDiabete());
        appointment.setMesureTension(request.getMesureTension());
        appointment.setNomAncienOperation(request.getNomAncienOperation());

        return appointmentMapper.entityToResult(appointmentRepository.save(appointment));
    }

   public AppointmentResult affectDoctor(UUID appointmentId, UUID doctorId) {
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


    public List<Object[]>getNumAppointmentsByMonthAndDoctor(UUID doctorId) {
        return appointmentRepository.getNumberAppointmentsByMonthAndDoctorId(doctorId);
    }

    public DoctorResult findMostFrequentDoctorId() {
        Object[] result = appointmentRepository.findMostFrequentDoctorId().get(0);
        UUID doctorId = (UUID) result[0];
        Long doctorCount = ((BigInteger) result[1]).longValue();
        User doctor = userRepository.findById(doctorId).orElseThrow(() -> new EntityNotFoundException("Doctor not found"));

        return DoctorResult.builder()
                .withId(doctorId)
                .withFirstname(doctor.getFirstname())
                .withLastname(doctor.getLastname())
                .withSpecialite(doctor.getSpecialite())
                .withImage(doctor.getImage())
                .withCount(doctorCount)
                .build();
   }



    public DevisResult createAppointmentDevis(UUID appointmentId, DevisRequest request) {
        var appointment = appointmentRepository.findById(appointmentId).orElseThrow(AppointmentNotFoundException::new);
        var devis = Devis.builder()
                .withId(UUID.randomUUID())
                .withCost(BigDecimal.valueOf(request.getCost()))
                .withIsApproved(false)
                .withCreatedOn(LocalDate.now())
                .withStatus(DevisStatus.CREATED)
                .build();
        appointment.setDevis(devis);
        appointment.setStatus(AppointmentStatus.WITH_DEVIS);
        appointment = appointmentRepository.save(appointment);
        return devisMapper.entityToResult(appointment.getDevis());
    }

    public DevisResult approveAppointmentDevis(UUID appointmentId) {
        var appointment = appointmentRepository.findById(appointmentId).orElseThrow(AppointmentNotFoundException::new);
        var devis = appointment.getDevis();
        devis.setApproved(true);
        devis.setStatus(DevisStatus.CONFIRMED_BY_PATIENT);
        devis.setValidatedOn(LocalDate.now());
        appointment = appointmentRepository.save(appointment);
        return devisMapper.entityToResult(appointment.getDevis());
    }
    public DevisResult rejectAppointmentDevis(UUID appointmentId) {
        var appointment = appointmentRepository.findById(appointmentId).orElseThrow(AppointmentNotFoundException::new);
        var devis = appointment.getDevis();
        devis.setApproved(false);
        devis.setStatus(DevisStatus.REJECTED_BY_PATIENT);
        devis.setValidatedOn(LocalDate.now());
        appointment = appointmentRepository.save(appointment);
        return devisMapper.entityToResult(appointment.getDevis());
    }

    public DevisResult updateAppointmentDevis(UUID appointmentId, DevisRequest request) {
        var appointment = appointmentRepository.findById(appointmentId).orElseThrow(AppointmentNotFoundException::new);
        var devis = appointment.getDevis();
        devis.setApproved(false);
        devis.setStatus(DevisStatus.CHANGED_BY_ADMIN);
        devis.setCost(BigDecimal.valueOf(request.getCost()));
        devis.setValidatedOn(LocalDate.now());
        appointment = appointmentRepository.save(appointment);
        return devisMapper.entityToResult(appointment.getDevis());
    }

    private String getCurrentUserName() {
        var currentUser = customUserDetailsService.getCurrentUser();
        return currentUser.getFirstname() + " " + currentUser.getLastname();
    }

    public List<Appointment> getCreatedAppointments() {
        return appointmentRepository.findByDevisStatus(DevisStatus.CREATED);

    }
    public Optional<Appointment> getCreatedDevisById(UUID appointmentId){
        return appointmentRepository.findByDevisStatusAndId(DevisStatus.CREATED, appointmentId);
    }
    public Optional<Appointment> getChangedDevisByAdminById(UUID appointmentId){
        return appointmentRepository.findByDevisStatusAndId(DevisStatus.CHANGED_BY_ADMIN, appointmentId);
    }
    public List<Appointment> getUpdateAppointmentsByAdmin() {
        return appointmentRepository.findByDevisStatus(DevisStatus.CHANGED_BY_ADMIN);
    }
    public List<Appointment> getApproveAppointmentsByPatient() {
        return appointmentRepository.findByDevisStatus(DevisStatus.CONFIRMED_BY_PATIENT);

    }

    public List<Appointment> getConfirmedAppointmentsByPatientId(UUID patientId) {
        return appointmentRepository.findByDevisStatusAndPatientId(DevisStatus.CONFIRMED_BY_PATIENT,patientId);

    }

    public List<Appointment> getConfirmedAppointmentsByDoctorId(UUID doctorId) {
        return appointmentRepository.findByDevisStatusAndDoctorId(DevisStatus.CONFIRMED_BY_PATIENT,doctorId);

    }
    public List<Appointment> getAllAppointmentsByDoctorIdWithStatus(UUID doctorId) {
        return appointmentRepository.findAppointmentByDoctorIdAndStatusIn(
                doctorId,
                Arrays.asList(
                        AppointmentStatus.IN_PROGRESS,
                        AppointmentStatus.ACCEPTED,
                        AppointmentStatus.WITH_DEVIS,
                        AppointmentStatus.CREATED
                )
        );
    }  public List<Appointment> getAllAppointmentsByChirurgieIdWithStatus(UUID chirurgieId) {
        return appointmentRepository.findAppointmentBySurgeryIdAndStatusIn(
                chirurgieId,
                Arrays.asList(
                        AppointmentStatus.IN_PROGRESS,
                        AppointmentStatus.ACCEPTED,
                        AppointmentStatus.WITH_DEVIS,
                        AppointmentStatus.CREATED
                )
        );
    }

    public void deleteAppointment(UUID id){
        this.appointmentRepository.deleteById(id);
    }


}

