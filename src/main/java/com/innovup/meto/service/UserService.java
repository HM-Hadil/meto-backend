package com.innovup.meto.service;

import com.innovup.meto.request.CreateUserRequest;
import com.innovup.meto.result.CreateUserResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    public CreateUserResult createNewUser(CreateUserRequest request) {
        return CreateUserResult.builder()
                .withFirstname(request.getFirstname())
                .withRole(request.getRole())
                .build();
        /* call userRepository.save(RequestToEntity(request)) */
    }
}
