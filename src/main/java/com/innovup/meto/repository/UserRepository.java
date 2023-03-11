package com.innovup.meto.repository;

import com.innovup.meto.entity.User;
import com.innovup.meto.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findAllByRole(Role role);

    Optional<User> findByEmail(String email);


}
