package com.example.test.repository;

import com.example.test.module.OptionalTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface
OptionalTestRepository extends JpaRepository<OptionalTest, UUID> {
}
