package com.innovup.meto.security.service;

import com.innovup.meto.entity.User;
import com.innovup.meto.security.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

public interface CustomUserDetailsService extends UserDetailsService {

    UserPrincipal loadUserById(UUID id);

    User getCurrentUser();

}
