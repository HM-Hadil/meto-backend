package com.innovup.meto.repository;

import com.innovup.meto.entity.SurgeriesRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface SurgeriesRequestRepository extends JpaRepository<SurgeriesRequest, UUID> {

    @Modifying
    @Query("delete from SurgeriesRequest sr where sr.id = :id")
    void deleteById(@Param("id") UUID id);
}
