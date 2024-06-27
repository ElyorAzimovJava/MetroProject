package com.example.test.repository;

import com.example.test.module.UserTestCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserTestCollectionRepository extends JpaRepository<UserTestCollection, UUID> {
    Optional<UserTestCollection> getUserTestCollectionByTestNumberAndTesterHistoryId(Integer code, UUID testerHistoryId);
    List<UserTestCollection> getUserTestCollectionByTesterHistoryId(UUID id);
    @Query(value = "select count(u.id) from UserTestCollection u where u.testerHistory.id= :testerId")
     Integer getTesterTotalTestCount(@Param("testerId") UUID testerId);
    @Query(value = "select count(u.id) from UserTestCollection u where u.testerHistory.id= :testerId and u.isTrueAnswer=true ")
     Integer getUserCorrectAnswers(@Param("testerId") UUID testerId);
}
