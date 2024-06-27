package com.example.test.repository;

import com.example.test.enums.ResultType;
import com.example.test.module.TesterResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TestResultRepository  extends JpaRepository<TesterResult, UUID> {
    Integer countByResultResultTypeAndTesterHistoryTesterPositionDepartmentName(ResultType resultType,String departmentId);
    Integer countByResultResultTypeAndTesterHistoryTesterPositionName(ResultType resultType,String departmentId);
    Integer countByResultResultType(ResultType resultType);
    List<TesterResult> getByTesterHistoryTesterId(UUID testerId);
}
