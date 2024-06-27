package com.example.test.repository;

import com.example.test.module.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TestRepository extends JpaRepository<Test, UUID> {
    @Query("select t.code from Test t order by t.code desc LIMIT 1")
    public Integer getLastCount();

}
