package com.example.test.repository;

import com.example.test.module.OptionalTest;
import com.example.test.module.TestSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TestSettingRepository extends JpaRepository<TestSettings, UUID> {
    @Query(value = "select  t.* from test_settings t limit 1", nativeQuery = true)
     TestSettings getTestSetting();
}
