package com.innovup.meto.service;

import com.innovup.meto.entity.User;
import com.innovup.meto.enums.Role;
import com.innovup.meto.repository.UserRepository;
import com.innovup.meto.request.CreateAdminRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public User createNewAdmin(CreateAdminRequest request) {
        var user = User.builder()
                .withId(UUID.randomUUID())
                .withFirstname(request.getFirstname())
                .withRole(Role.ADMIN)
                .withCreatedOn(LocalDate.now())
                .build();
        /*
        ObjectMapper.map(user, createUserResult)
         */
        user = userRepository.save(user);
        return user;
    }
}
