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


}
