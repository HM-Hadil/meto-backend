package com.innovup.meto.repository;

import com.innovup.meto.entity.Chirurgie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChirurgieRepo extends JpaRepository<Chirurgie, UUID> {



}