package com.innovup.meto.security.service;

import com.innovup.meto.entity.User;
import com.innovup.meto.repository.UserRepository;
import com.innovup.meto.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private final UserRepository userRepository;

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        var authorities = new HashSet<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
        return authorities;
    }

    @Override
    public UserPrincipal loadUserById(UUID id) {
        var optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            var user = optionalUser.get();
            return UserPrincipal.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .isEnabled(user.isEnabled())
                    .isAccountNonExpired(true)
                    .isAccountNonLocked(true)
                    .isCredentialsNonExpired(true)
                    .authorities(getAuthority(user))
                    .build();
        } else {
            log.error("<ERROR>:User not found [id: {}]", id);
            throw new UsernameNotFoundException("User not found [toEmail: " + id + "]");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var optionalUser = userRepository.findByEmail(username);
        if (optionalUser.isPresent()) {
            var user = optionalUser.get();
            return UserPrincipal.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .isEnabled(user.isEnabled())
                    .isAccountNonExpired(true)
                    .isAccountNonLocked(true)
                    .isCredentialsNonExpired(true)
                    .authorities(getAuthority(user))
                    .build();
        } else {
            log.error("<ERROR>:User not found [login/toEmail: {}]", username);
            throw new UsernameNotFoundException("User not found [toEmail: " + username + "]");
        }
    }

    @Override
    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userPrincipal = (UserPrincipal) authentication.getPrincipal();
        var userOptional = userRepository.findById(userPrincipal.getId());
        return userOptional.orElse(null);
    }



}

