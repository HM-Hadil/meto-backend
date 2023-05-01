package com.innovup.meto.repository;

import com.innovup.meto.entity.Chirurgie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChirurgieRepo extends JpaRepository<Chirurgie, UUID> {

    @Query(value =  "SELECT COUNT(*) as surgery_count FROM Chirurgie ")
    public int getChirurgieCount();


    @Query(value="SELECT surgery_id, COUNT(DISTINCT user_id)  FROM users_surgeries GROUP BY surgery_id", nativeQuery = true)
    List<Object[]> getNumDoctorsPerSurgery();

}