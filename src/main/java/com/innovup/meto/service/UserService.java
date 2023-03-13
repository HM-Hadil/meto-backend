package com.innovup.meto.service;

import com.innovup.meto.entity.User;
import com.innovup.meto.enums.Role;
import com.innovup.meto.repository.UserRepository;
import com.innovup.meto.request.CreateAdminRequest;
import com.innovup.meto.request.CreateDoctorRequest;
import com.innovup.meto.request.CreatePatientRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public User createNewAdmin(CreateAdminRequest request) {
        var user = User.builder()
                .withId(UUID.randomUUID())
                .withFirstname(request.getFirstname())
                .withEmail(request.getEmail())
                .withPassword(passwordEncoder.encode(request.getPassword()))
                .withLastname(request.getLastname())
                .withRole(Role.ADMIN)
                .withCreatedOn(LocalDate.now())
                .build();

        // ObjectMapper.map(user, createUserResult)

         return userRepository.save(user);
    }

    public User createNewPatient(CreatePatientRequest request) {
        var user = User.builder()
                .withId(UUID.randomUUID())
                .withFirstname(request.getFirstname())
                .withLastname(request.getLastname())
                .withEmail(request.getEmail())
                .withPassword(passwordEncoder.encode(request.getPassword()))
                .withRole(Role.PATIENT)
                .withCreatedOn(LocalDate.now())
                .build();
        return userRepository.save(user);
    }

    public User createNewDoctor(CreateDoctorRequest request) {

        var user = User.builder()
                .withId(UUID.randomUUID())
                .withFirstname(request.getFirstname())
                .withLastname(request.getLastname())
                .withEmail(request.getEmail())
                .withPassword(passwordEncoder.encode(request.getPassword()))
                .withVille(request.getVille())
                .withSpecialite(request.getSpecialite())
                .withSexe(request.getSexe())
                .withExperience(request.getExperience())
                .withParcours(request.getParcours())
                .withImage(request.getImage())
                .withAdresse(request.getAdresse())
                .withRole(Role.DOCTOR)
                .withCreatedOn(LocalDate.now())
                .build();
        return userRepository.save(user);
    }

    public User findById(UUID uuid) {
        Optional<User> optional = userRepository.findById(uuid);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException("User with id {} not found" + uuid);
    }
}
