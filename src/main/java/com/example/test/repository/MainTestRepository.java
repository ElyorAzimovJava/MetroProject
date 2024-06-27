package com.example.test.repository;

import com.example.test.module.MainTest;
import com.example.test.module.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MainTestRepository extends JpaRepository<MainTest, UUID> {
     Optional<MainTest> findByPositionName(String positionName);
     Boolean existsByName(String name);
}
