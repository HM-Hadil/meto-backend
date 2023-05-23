package com.innovup.meto.repository;

import com.innovup.meto.entity.Devis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DevisRepository extends JpaRepository<Devis, UUID> {

}
