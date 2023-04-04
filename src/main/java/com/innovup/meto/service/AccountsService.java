package com.innovup.meto.service;

import com.innovup.meto.entity.User;
import com.innovup.meto.enums.Role;
import com.innovup.meto.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountsService {

    private final UserRepository userRepository;

    public List<User> findDisabledDoctor() {
        return userRepository.findByRoleAndIsEnabled(Role.DOCTOR,false);
    }

   public  Optional< User> getUserByIdWithRoleAndEnabledFalse(UUID id) {
        return userRepository.findByIdAndRoleAndIsEnabledFalse(id, Role.DOCTOR);
    }
    public List<User> findDisabledPatient() {
        return userRepository.findByRoleAndIsEnabled(Role.PATIENT,false);
    }

    public  Optional< User> getUsersByIdWithRoleAndEnabledFalse(UUID id) {
        return userRepository.findByIdAndRoleAndIsEnabledFalse(id, Role.PATIENT);
    }

    //delete accounts
    public void DeleteAccounts(UUID id){
         this.userRepository.deleteById(id);
    }

    //activate Accounts of doctors
    public User Activate(UUID id) {
        var userOptional = userRepository.findById(id);

        if (userOptional.isPresent() && userOptional.get().isEnabled()==false ) {
            var user = userOptional.get();
            user.setEnabled((true));


            return userRepository.save(user);
        } else {
            throw new RuntimeException();
        }
    }
    public  Optional< User> getUserByIdWithRoleAndEnabledTrue(UUID id) {
        return userRepository.findByIdAndRoleAndIsEnabledTrue(id, Role.DOCTOR);
    }

}
