package com.innovup.meto.repository;

import com.innovup.meto.entity.ChirurgieRequestDoctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface  ChirurgieRequestRepository extends JpaRepository<ChirurgieRequestDoctor, UUID> {
}
