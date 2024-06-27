package com.example.test.repository;

import com.example.test.module.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ResultRepository extends JpaRepository<Result, UUID> {
    @Query("select r from Result r where r.startPercentage<= :per and r.endPercentage>= :per")
    Optional<Result> getCorrectResult(@Param("per") Integer percentage);
}
