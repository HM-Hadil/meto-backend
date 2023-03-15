package com.innovup.meto.typeChirurgie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChirurgieRepo extends JpaRepository<Chirurgie, Long> {



}