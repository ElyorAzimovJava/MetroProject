package com.example.test.repository;

import com.example.test.module.Department;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {
    Boolean existsByNameAndIsDeletedFalse(String name);
    List<Department> findByIsDeletedFalse();
    Optional<Department> findByName(String name);
    List<Department> findAllByIsDeletedFalseOrderByCreatedDateDesc(Pageable pageable);

  @Query("select count(d.id) from Department d where d.isDeleted=false")
    Integer getDepartmentActiveDepartmentCount();

    Optional<Department> findByIdAndIsDeletedFalse(UUID id);
}
