package com.example.test.repository;

import com.example.test.module.Test;
import com.example.test.module.Tester;
import jakarta.persistence.TemporalType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TesterRepository extends JpaRepository<Tester, UUID> {

    Optional<Tester> findByIdNumber(String idNumber);
    Boolean existsByIdNumber(String idNumber);
    Boolean existsByIdNumberOrPassportSerial(String idNumber, String passportSeria);
    Integer countByPositionDepartmentId(UUID departmentId);
    Integer countByPositionName(String positionName);
    @Query("select count(t.id) from Tester t")
    Integer countAll();
    List<Tester>findAllByFirstNameContainingIgnoreCaseOrMiddleNameContainingIgnoreCaseAndPositionId(
            String firstName, String lastName,UUID positionId, Pageable pageable
    );
    List<Tester>findAllByFirstNameContainingIgnoreCaseOrMiddleNameContainingIgnoreCaseAndPositionDepartmentId(
            String fistName, String lastName, UUID departmentId,Pageable pageable
    );
    List<Tester> findAllByFirstNameContainingIgnoreCaseOrMiddleNameContainingIgnoreCase(
            String firstName, String lastName,Pageable pageable
    );
    List<Tester> getAllBy(Pageable pageable);
    List<Tester> findAllByPositionDepartmentId(UUID departmentId, Pageable pageable);
    List<Tester> findAllByPositionName(String positionName, Pageable pageable);
    Integer countAllByFirstNameContainingIgnoreCaseOrMiddleNameContainingIgnoreCaseAndPositionId(
            String firstName, String lastName,UUID positionId
    );
    Integer countAllByFirstNameContainingIgnoreCaseOrMiddleNameContainingIgnoreCaseAndPositionDepartmentId(
            String fistName, String lastName, UUID departmentId
    );
    Integer countAllByPositionName(String positionName);
   Integer countAllByPositionDepartmentId(UUID departmentId);
   Integer countAllByFirstNameContainingIgnoreCaseOrMiddleNameContainingIgnoreCase( String firstName, String lastName);

}
