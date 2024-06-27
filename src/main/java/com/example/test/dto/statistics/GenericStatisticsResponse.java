package com.example.test.dto.statistics;

import com.example.test.dto.result.ResultResponseForStatistics;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenericStatisticsResponse {
    private Integer totalTesterCount;
    private Integer totalTestHistoryCount;
    private Integer totalDepartmentCount;
    private Integer totalPositionCount;
    private List<ResultResponseForStatistics> result;
}
