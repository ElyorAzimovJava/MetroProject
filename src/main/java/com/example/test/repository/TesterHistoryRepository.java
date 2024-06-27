package com.example.test.repository;

import com.example.test.module.TesterHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface TesterHistoryRepository extends JpaRepository<TesterHistory,UUID> {
    @Query("select count(t.id) from TesterHistory t")
    Integer countOfHistory();
    @Query("select count(t.id) from TesterHistory t where t.tester.position.department.id = :departmentId")
    Integer countOfHistoryByDepartment(@Param("departmentId") UUID departmentId);
    @Query("select count(t.id) from TesterHistory t where t.tester.position.id =: positionId")
    Integer countOfHistoryByPosition(@Param("departmentId") UUID position);
}
