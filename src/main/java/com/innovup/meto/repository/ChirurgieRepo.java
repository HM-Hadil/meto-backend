package com.innovup.meto.repository;

import com.innovup.meto.entity.Chirurgie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChirurgieRepo extends JpaRepository<Chirurgie, UUID> {

    @Query(value =  "SELECT COUNT(*) as surgery_count FROM Chirurgie ")
    public int getChirurgieCount();

}