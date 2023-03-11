package com.innovup.meto.service;

import com.innovup.meto.entity.User;
import com.innovup.meto.enums.Role;
import com.innovup.meto.repository.UserRepository;
import com.innovup.meto.request.CreateAdminRequest;
import com.innovup.meto.request.CreateDoctorRequest;
import com.innovup.meto.request.CreatePatientRequest;
import com.innovup.meto.security.auth.AuthenticationRequest;
import com.innovup.meto.security.auth.AuthenticationResponse;
import com.innovup.meto.security.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final  JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse createNewAdmin(CreateAdminRequest request) {
        var user = User.builder()
                .withId(UUID.randomUUID())
                .withFirstname(request.getFirstname())
                .withEmail(request.getEmail())
                .withPassword(passwordEncoder.encode(request.getPassword()))
                .withLastname(request.getLastname())
                .withRole(Role.ADMIN)
                .withCreatedOn(LocalDate.now())
                .build();
        /*
        ObjectMapper.map(user, createUserResult)
         */
         userRepository.save(user);
        var jwtToken = jwtTokenUtil.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

    public AuthenticationResponse createNewPatient(CreatePatientRequest request) {
        var user = User.builder()
                .withId(UUID.randomUUID())
                .withFirstname(request.getFirstname())
                .withLastname(request.getLastname())
                .withEmail(request.getEmail())
                .withPassword(passwordEncoder.encode(request.getPassword()))
                .withRole(Role.PATIENT)
                .withCreatedOn(LocalDate.now())
                .build();
        userRepository.save(user);
        var jwtToken = jwtTokenUtil.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse createNewDoctor(CreateDoctorRequest request) {

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
        userRepository.save(user);
        var jwtToken = jwtTokenUtil.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtTokenUtil.generateToken(user);
        return  AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

}
