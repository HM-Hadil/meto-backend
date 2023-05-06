package com.innovup.meto.service;

import com.innovup.meto.entity.AcademicLevel;
import com.innovup.meto.entity.Appointment;
import com.innovup.meto.entity.Experience;
import com.innovup.meto.enums.Role;
import com.innovup.meto.exception.UserNotFoundException;
import com.innovup.meto.mapper.UserMapper;
import com.innovup.meto.repository.AppointmentRepository;
import com.innovup.meto.repository.UserRepository;
import com.innovup.meto.request.CreateDoctorRequest;
import com.innovup.meto.request.ExperienceRequest;
import com.innovup.meto.request.ParcoursRequest;
import com.innovup.meto.result.DoctorResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service

public  class DoctorsService extends UserService<DoctorResult> {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AppointmentRepository appointmentRepository;

    private final ChirurgieRequestService surgeriesRequestService;

    protected DoctorsService(UserRepository repository, UserMapper userMapper, AppointmentRepository appointmentRepository, ChirurgieRequestService service) {
        super(Role.DOCTOR, repository);
        this.userRepository = repository;
        this.userMapper = userMapper;
        this.appointmentRepository = appointmentRepository;
        surgeriesRequestService = service;
    }

    public List<DoctorResult> findAll() {
        return userRepository.findAllByRole(Role.DOCTOR).stream()
                .map(userMapper::entityToDoctor)
                .toList();
    }

    public List<DoctorResult>  findAllDoctorsBySurgeryId(UUID surgeryId) {
        return userRepository.findAllBySpecialitesId(surgeryId).stream()
                .map(userMapper::entityToDoctor)
                .toList();
    }

    public DoctorResult findById(UUID id) {
        return userMapper.entityToDoctor(userRepository.findById(id).orElse(null));
    }

    public DoctorResult update(UUID doctorId, CreateDoctorRequest request ) {
        var doctor = userRepository.findById(doctorId);
        if (doctor.isPresent()) {
            var user = doctor.get();
            user.setId(doctorId);
            user.setFirstname(request.getFirstname());
            user.setLastname(request.getLastname());
            user.setTelephone(request.getTelephone());
            user.setSpecialite(request.getSpecialite());

            user.setAdresse(request.getAdresse());
            user.setVille(request.getVille());
            user.setParcours(createAcademicLevels(request.getParcours()));
            user.setExperience(createExperiences(request.getExperience()));
            return userMapper.entityToDoctor(userRepository.save(user));
        } else {
            throw new UserNotFoundException(Role.DOCTOR);
        }
    }

    public DoctorResult updatePhoto(UUID id, CreateDoctorRequest request) {
        var userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            var user = userOptional.get();
            user.setId(id);
            user.setImage(request.getImage());

            return userMapper.entityToDoctor(userRepository.save(user));
        } else {
            throw new UserNotFoundException(Role.DOCTOR);
        }
    }


/**
    public com.innovup.meto.entity.ChirurgieRequest createSurgeryRequest(UUID doctorId, ChirurgieRequest req){
        return surgeriesRequestService.createSurgeryRequest(doctorId, req);
    }
**/
    private List<AcademicLevel> createAcademicLevels(List<ParcoursRequest> academicLevelRequests) {
        return academicLevelRequests.stream()
                .map(this::newAcademicLevel)
                .toList();
    }
    public boolean isDoctorAvailable(UUID doctorId, LocalDateTime dateRDV) {
        List<Appointment> appointments = appointmentRepository.findAppointmentByDoctorId(doctorId);

        for (Appointment appointment : appointments) {
            if (appointment.getDateRDV().isEqual(dateRDV)) {
                return false;
            }
        }

        return true;
    }







    private AcademicLevel newAcademicLevel(ParcoursRequest request) {
        return AcademicLevel.builder()
                .withId(UUID.randomUUID())
                .withField(request.getField())
                .withDiploma(request.getDiploma())
                .withEstablishment(request.getEstablishment())
                .build();
    }

    private List<Experience> createExperiences(List<ExperienceRequest> experienceRequests) {
        return experienceRequests.stream()
                .map(this::newExperience)
                .toList();
    }

    private Experience newExperience(ExperienceRequest request) {
        return Experience.builder()
                .withId(UUID.randomUUID())
                .withSpecialty(request.getSpecialty())
                .withEstablishment(request.getEstablishment())
                .build();
    }

}