package com.innovup.meto.service;

import com.innovup.meto.entity.Chirurgie;
import com.innovup.meto.entity.ChirurgieDuration;
import com.innovup.meto.entity.User;
import com.innovup.meto.enums.Role;
import com.innovup.meto.exception.SurgeryNotFoundException;
import com.innovup.meto.mapper.ChirurgieMapper;
import com.innovup.meto.mapper.UserMapper;
import com.innovup.meto.repository.ChirurgieRepo;
import com.innovup.meto.repository.ChirurgieRequestRepository;
import com.innovup.meto.repository.UserRepository;
import com.innovup.meto.request.ChirurgieRequest;
import com.innovup.meto.request.CreateAdminRequest;
import com.innovup.meto.result.AdministratorResult;
import com.innovup.meto.result.AppointmentResult;
import com.innovup.meto.result.ChirurgieResult;
import com.innovup.meto.util.DurationConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdminService extends UserService<AdministratorResult>{

    private final UserRepository userRepository;
    private final AppointmentService appointmentService;
    private final ChirurgieRepo surgeryRepository;
    private final UserMapper userMapper;
    private final ChirurgieMapper surgeryMapper;
    private final ChirurgieRequestRepository surgeriesRequestRepository;
    protected AdminService(UserRepository repository, AppointmentService appointmentService, ChirurgieRepo surgeryRepository, UserMapper userMapper, ChirurgieMapper surgeryMapper, ChirurgieRequestRepository surgeriesRequestRepository) {
        super(Role.ADMIN, repository);
        this.userRepository = repository;
        this.appointmentService = appointmentService;
        this.surgeryRepository = surgeryRepository;
        this.userMapper = userMapper;
        this.surgeryMapper = surgeryMapper;
        this.surgeriesRequestRepository = surgeriesRequestRepository;
    }


    public List<AdministratorResult> findAll() {
        return userRepository.findAllByRole(Role.ADMIN).stream()
                .map(userMapper::entityToAdministrator)
                .toList();
    }

    public AdministratorResult findById(UUID id) {
        return userMapper.entityToAdministrator(userRepository.findById(id).orElse(null));
    }
    public User update(UUID id, CreateAdminRequest request) {
        var userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            var user = userOptional.get();
            user.setFirstname(request.getFirstname());
            user.setLastname(request.getLastname());
            user.setEmail(request.getEmail());
            return userRepository.save(user);
        } else {
            return null;
        }
    }

    public AppointmentResult validateAppointment(UUID adminId, UUID appointmentId) {
        return appointmentService.validateAppointment(adminId, appointmentId, null);
    }

    public AppointmentResult validateAppointmentWithDoctorId(UUID adminId, UUID appointmentId, UUID doctorId) {
        return appointmentService.validateAppointment(adminId, appointmentId, doctorId);
    }

    public ChirurgieResult approveSurgeryRequest(UUID surgeryId, ChirurgieRequest request) {
        var surgeryRequest = surgeriesRequestRepository.findById(surgeryId).orElseThrow(SurgeryNotFoundException::new);
        var surgery = Chirurgie.builder()
                .withId(surgeryRequest.getId())
                .withName(request.getName())
                .withDescription(request.getDescription())
                .withImage(request.getImage())
                .withDuration(
                        ChirurgieDuration.builder()
                                .withDays(request.getDuration().getDays())
                                .withHours(request.getDuration().getHours())
                                .withMinutes(request.getDuration().getMinutes())
                                .withSeconds(request.getDuration().getSeconds())
                                .build()
                )
                .withDurationInSeconds(DurationConverter.toSeconds(request.getDuration()))
                .build();
        surgeriesRequestRepository.delete(surgeryRequest);
        surgery = surgeryRepository.save(surgery);
        return surgeryMapper.entityToResult(surgery);
    }
}
