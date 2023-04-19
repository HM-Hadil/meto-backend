package com.innovup.meto.service;

import com.innovup.meto.entity.User;
import com.innovup.meto.enums.Role;
import com.innovup.meto.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountsService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public List<User> findDisabledDoctor() {
        return userRepository.findByRoleAndIsEnabled(Role.DOCTOR, false);
    }

    public Optional<User> getUserByIdWithRoleAndEnabledFalse(UUID id) {
        return userRepository.findByIdAndRoleAndIsEnabledFalse(id, Role.DOCTOR);
    }

    public List<User> findDisabledPatient() {
        return userRepository.findByRoleAndIsEnabled(Role.PATIENT, false);
    }

    public List<User> findAllActivatePatient() {
        return userRepository.findByRoleAndIsEnabled(Role.PATIENT, true);
    }

    public List<User> findAllActiveDoctor(String searchKeyWord) {
        String search = searchKeyWord == null ? "" : searchKeyWord.toLowerCase();
        return userRepository.findByRoleAndIsEnabledTrue(Role.DOCTOR)
                .stream()
                .filter(user -> search.isEmpty() || user.getFirstname().toLowerCase().contains(search))
                .collect(Collectors.toList());
    }

    public Optional<User> getUsersByIdWithRoleAndEnabledFalse(UUID id) {
        return userRepository.findByIdAndRoleAndIsEnabledFalse(id, Role.PATIENT);
    }

    //delete accounts
    public void DeleteAccounts(UUID id) {
        this.userRepository.deleteById(id);
    }

    //activate Accounts of doctors
    public User Activate(UUID id) {
        var userOptional = userRepository.findById(id);

        if (userOptional.isPresent() && userOptional.get().isEnabled() == false) {
            var user = userOptional.get();
            user.setEnabled((true));


            return userRepository.save(user);
        } else {
            throw new RuntimeException();
        }
    }

    //desactivate account
    public User Desactivate(UUID id) {
        var userOptional = userRepository.findById(id);

        if (userOptional.isPresent() && userOptional.get().isEnabled() == true) {
            var user = userOptional.get();
            user.setEnabled((false));


            return userRepository.save(user);
        } else {
            throw new RuntimeException();
        }
    }

    public Optional<User> getUserByIdWithRoleAndEnabledTrue(UUID id) {
        return userRepository.findByIdAndRoleAndIsEnabledTrue(id, Role.DOCTOR);
    }

    public Optional<User> getPatientByIdWithRoleAndEnabledTrue(UUID id) {
        return userRepository.findByIdAndRoleAndIsEnabledTrue(id, Role.PATIENT);
    }

    public List<User> searchDoctor(String query) {
        return userRepository.searchDoctor(query);
    }

    public List<Object[]> countPatientPerGender() {
        return userRepository.countPatientPerGender();
    }


    public void changePassword(String email, String oldPassword, String newPassword) {
        var optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            var user = optionalUser.get();
            if (passwordEncoder.matches(oldPassword, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
            } else {
                throw new IllegalArgumentException("Invalid old password.");
            }
        } else {
            throw new UsernameNotFoundException("User not found [toEmail: " + email + "]");
        }
    }


    public int getDoctorCount() {
        return userRepository.getDoctorCount();
    }
    public int getPatientCount() {
        return userRepository.getPatientCount();
    }

}