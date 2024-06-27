package com.example.test.dto.statistics;

import com.example.test.dto.result.ResultResponseForStatistics;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StatisticsOfDepartments {
    private UUID id;
    private String name;
    private Integer totalTesterCount;
    private Integer totalTesterHistoryCount;
    private Double percentageOfTesterHistory;
    private Double percentageOfTester;
    List<ResultResponseForStatistics> conclusions;
}
