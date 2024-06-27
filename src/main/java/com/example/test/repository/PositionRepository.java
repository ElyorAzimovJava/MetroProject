package com.example.test.repository;

import com.example.test.module.Position;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface PositionRepository extends JpaRepository<Position, UUID> {
    Optional<Position> findByName(String positionName);
    @Query(value = "select p from Position p where p.department.id = :departmentId")
    List<Position> getByDepartmentIdIAndIsDeletedFalse(@Param("departmentId") UUID departmentId);
    @Query(value = "select p from Position p where p.isForTestOrRole=true")
    List<Position> getAllPositions();
    Optional<Position> findByIsDeletedFalseAndId(UUID id);

    @Query(value = "select distinct ON (name) * from position p where p.is_deleted=false",nativeQuery = true)
    List<Position> getByPositionNameUnique();
    @Query(value = "select distinct ON (name) * from position p where p.is_deleted=false and p.is_for_test_or_role = true",nativeQuery = true)
    List<Position> getByRealPositionNameUnique();
    @Query(value = "select distinct ON (name) * from position p where p.is_for_test_or_role = true ",nativeQuery = true)
    List<Position> getByPositionNameUniqueAndPosition();
    @Query("select exists (select p from Position p where p.isDeleted = true and p.name=:name and p.department.id = :departmentId )")
     Boolean uniquePositionNameInDepartment(@Param("name") String name,@Param("departmentId") UUID departmentId);

    Integer countAllByIsDeletedFalse();
    List<Position> getAllByIsDeletedFalse(Pageable pageable);


    Optional<Position> findByIdAndIsDeletedFalse(UUID id);
    @Query("select p from  Position p where p.isForTestOrRole = false ")
    List<Position> getGenericPositions();
   /* @Query("select exists (select p from  Position p where p.isForTestOrRole = false and p.name =: name )")
    Boolean existByNameGenericPosition(@Param("name") String name);*/
    Boolean existsByNameAndIsDeletedFalse(String name);

    Boolean existsByNameAndDepartmentId(String name, UUID departmentId);
    @Query(value = "select p.* from position p where p.is_for_test_or_role=false limit 2",nativeQuery = true)
    List<Position> getRandomPositions();

}
