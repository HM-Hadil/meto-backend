package com.innovup.meto.repository;

import com.innovup.meto.entity.ChirurgieRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface  ChirurgieRequestRepository extends JpaRepository<ChirurgieRequest, UUID> {
}
