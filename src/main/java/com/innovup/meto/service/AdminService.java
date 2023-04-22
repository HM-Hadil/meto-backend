package com.innovup.meto.service;

import com.innovup.meto.entity.Surgery;
import com.innovup.meto.entity.SurgeryDuration;
import com.innovup.meto.entity.User;
import com.innovup.meto.enums.Role;
import com.innovup.meto.exception.SurgeryNotFoundException;
import com.innovup.meto.mapper.SurgeryMapper;
import com.innovup.meto.mapper.UserMapper;
import com.innovup.meto.repository.SurgeriesRequestRepository;
import com.innovup.meto.repository.SurgeryRepository;
import com.innovup.meto.repository.UserRepository;
import com.innovup.meto.request.CreateAdminRequest;
import com.innovup.meto.request.SurgeryRequest;
import com.innovup.meto.result.AdministratorResult;
import com.innovup.meto.result.AppointmentResult;
import com.innovup.meto.result.SurgeryResult;
import com.innovup.meto.utils.DurationConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdminService extends UserService<AdministratorResult> {

    private final UserRepository userRepository;
    private final AppointmentService appointmentService;
    private final UserMapper userMapper;
    private final SurgeriesRequestRepository surgeriesRequestRepository;
    private final SurgeryRepository surgeryRepository;
    private final SurgeryMapper surgeryMapper;

    protected AdminService(
            UserRepository userRepository,
            AppointmentService appointmentService,
            SurgeriesRequestRepository surgeriesRequestRepository,
            SurgeryRepository surgeryRepository,
            UserMapper userMapper,
            SurgeryMapper surgeryMapper
    ) {
        super(Role.ADMIN, userRepository);
        this.userRepository = userRepository;
        this.appointmentService = appointmentService;
        this.surgeriesRequestRepository = surgeriesRequestRepository;
        this.surgeryRepository = surgeryRepository;
        this.userMapper = userMapper;
        this.surgeryMapper = surgeryMapper;
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

    public SurgeryResult approveSurgeryRequest(UUID surgeryId, SurgeryRequest request) {
        var surgeryRequest = surgeriesRequestRepository.findById(surgeryId).orElseThrow(SurgeryNotFoundException::new);
        var surgery = Surgery.builder()
                .withId(surgeryRequest.getId())
                .withName(request.getName())
                .withDescription(request.getDescription())
                .withImage(request.getImage())
                .withDuration(
                        SurgeryDuration.builder()
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
