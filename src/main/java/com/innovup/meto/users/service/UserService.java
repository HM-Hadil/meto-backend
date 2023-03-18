package com.innovup.meto.users.service;

import com.innovup.meto.users.entity.User;
import com.innovup.meto.users.enums.Role;
import com.innovup.meto.users.repository.UserRepository;
import com.innovup.meto.users.request.CreateAdminRequest;
import com.innovup.meto.users.request.CreateDoctorRequest;
import com.innovup.meto.users.request.CreatePatientRequest;
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
                .withIsEnabled(true)
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
                .withTelephone(request.getTelephone())
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

    //update profile doctor
    public User updateProfile(UUID id,CreateDoctorRequest request){
        User user = userRepository.findUserById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setVille(request.getVille());
        user.setSpecialite(request.getSpecialite());
        user.setSexe(request.getSexe());
        user.setExperience(request.getExperience());
        user.setTelephone(request.getTelephone());
        user.setParcours(request.getParcours());
        user.setImage(request.getImage());
        user.setAdresse(request.getAdresse());

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
