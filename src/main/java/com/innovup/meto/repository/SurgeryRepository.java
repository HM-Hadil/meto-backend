package com.innovup.meto.repository;

import com.innovup.meto.entity.Surgery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SurgeryRepository extends JpaRepository<Surgery, UUID> {
    void deleteById(UUID id);
}
