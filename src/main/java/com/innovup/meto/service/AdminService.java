package com.innovup.meto.service;

import com.innovup.meto.entity.User;
import com.innovup.meto.enums.Role;
import com.innovup.meto.mapper.UserMapper;
import com.innovup.meto.repository.UserRepository;
import com.innovup.meto.request.CreateAdminRequest;
import com.innovup.meto.result.AdministratorResult;
import com.innovup.meto.result.AppointmentResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdminService extends UserService<AdministratorResult>{

    private final UserRepository userRepository;
    private final AppointmentService appointmentService;
    private final UserMapper userMapper;
    protected AdminService(UserRepository repository, AppointmentService appointmentService,UserMapper userMapper) {
        super(Role.ADMIN, repository);
        this.userRepository = repository;
        this.appointmentService = appointmentService;
        this.userMapper = userMapper;
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
}
