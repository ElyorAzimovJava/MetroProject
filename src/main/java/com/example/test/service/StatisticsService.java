package com.example.test.service;

import com.example.test.dto.result.ResultResponseForStatistics;
import com.example.test.dto.statistics.GenericStatisticsResponse;
import com.example.test.dto.statistics.StatisticsOfDepartments;
import com.example.test.module.Department;
import com.example.test.module.Position;
import com.example.test.module.Result;
import com.example.test.module.TesterHistory;
import com.example.test.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final DepartmentRepository departmentRepository;
    private final ResultRepository resultRepository;
    private final PositionRepository positionRepository;
    private final TesterRepository testerRepository;
    private final TestResultRepository testResultRepository;
    private final TesterHistoryRepository testerHistoryRepository;

    public List<StatisticsOfDepartments> statisticsOfDepartments(){
        List<Department> getActiveDepartments = departmentRepository.findByIsDeletedFalse();
        Integer allTesterCount = testerRepository.countAll();
        Integer allTesterHistory = testerHistoryRepository.countOfHistory();
      return  getActiveDepartments.stream()
                .map(department->mapToStatisticsOfDepartment(department,allTesterCount,allTesterHistory))
                .collect(Collectors.toList());


    }
    public List<StatisticsOfDepartments> statisticsOfPositions(){
        List<Position> byPositionNameUnique = positionRepository.getByPositionNameUniqueAndPosition();
        Integer allTesterHistory = testerHistoryRepository.countOfHistory();
        Integer allTesterCount = testerRepository.countAll();
        return byPositionNameUnique.stream()
                .map(position -> mapToStatisticsOfPosition(position,allTesterCount,allTesterHistory))
                .collect(Collectors.toList());
    }


    public StatisticsOfDepartments mapToStatisticsOfDepartment(Department department, Integer totalTesters,Integer totalTesterHistoryCount){
        Integer departmentTesters = countOfTesterInDepartment(department.getId());
        Integer departmentTesterHistory = testerHistoryRepository.countOfHistoryByDepartment(department.getId());
        Double percentageOfTesterHistory = getPercentage(totalTesterHistoryCount,departmentTesterHistory);
        List<Result> all = resultRepository.findAll();
        List<ResultResponseForStatistics> results = all.stream()
                .map(result -> mapToResponse(result, departmentTesters, department.getName(),1))
                .collect(Collectors.toList());
        return new StatisticsOfDepartments(
                department.getId(),
                department.getName(),
                departmentTesters,
                departmentTesterHistory,
                 percentageOfTesterHistory,
                getPercentage(totalTesters, departmentTesters),
                results
        );
    }


    public GenericStatisticsResponse statisticsForGeneric() {
        Integer activeDepartmentCount = departmentRepository.getDepartmentActiveDepartmentCount();
        Integer activePositionCount = positionRepository.countAllByIsDeletedFalse();
        Integer testerCount = testerRepository.countAll();
        Integer testerHistoryCount = testerHistoryRepository.countOfHistory();
        List<Result> all = resultRepository.findAll();
        List<ResultResponseForStatistics> getResults = all.stream()
                .map(result -> mapToResponse(result, testerCount, null, 3))
                .collect(Collectors.toList());
        return new GenericStatisticsResponse(
                testerCount,testerHistoryCount,activeDepartmentCount,activePositionCount,getResults
        );

    }

    private StatisticsOfDepartments mapToStatisticsOfPosition(Position position, Integer totalTesters,Integer totalTesterHistoryCount){
        Integer positionTesters = countOfTesterInPosition(position.getName());
        List<Result> all = resultRepository.findAll();
        Integer positionTesterHistory = testerHistoryRepository.countOfHistoryByPosition(position.getId());
        Double percentageOfTesterHistory = getPercentage(totalTesterHistoryCount,positionTesterHistory);
        List<ResultResponseForStatistics> results = all.stream()
                .map(result -> mapToResponse(result, positionTesters, position.getName(),3))
                .collect(Collectors.toList());
        return new StatisticsOfDepartments(
                position.getId(),
                position.getName(),
                positionTesters,
                positionTesterHistory,
                percentageOfTesterHistory,
                getPercentage(totalTesters, positionTesters),
                results
        );
    }

    private ResultResponseForStatistics mapToResponse(Result result,Integer testersCount,String name,Integer num){
        Integer countOfResult = 0;
        if(num ==1)
         countOfResult = testResultRepository.countByResultResultTypeAndTesterHistoryTesterPositionDepartmentName(result.getResultType(), name);
        else if(num == 2) countOfResult = testResultRepository.countByResultResultTypeAndTesterHistoryTesterPositionName(result.getResultType(),name);
        else countOfResult = testResultRepository.countByResultResultType(result.getResultType());

        return new ResultResponseForStatistics(
                result.getDescription(),
                result.getResultType(),
                result.getStartPercentage(),
                result.getEndPercentage(),
                countOfResult,
                getPercentage(testersCount,countOfResult)
        );
    }
    private Integer countOfTesterInPosition(String positionName){
        return testerRepository.countByPositionName(positionName);
    }
    private Double getPercentage(Integer totalCount,Integer testerCount){
        if(testerCount == 0) return 0.0;
        double d = ((double) testerCount * 100 / (double) totalCount);
        long value = Math.round(d * 100);
        return (double) value  / 100;
    }
    private Integer countOfTesterInDepartment(UUID departmentId){
        return testerRepository.countByPositionDepartmentId(departmentId);
    }
}
