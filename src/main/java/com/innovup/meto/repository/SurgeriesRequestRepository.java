package com.innovup.meto.repository;

import com.innovup.meto.entity.SurgeriesRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SurgeriesRequestRepository extends JpaRepository<SurgeriesRequest, UUID> {
}
