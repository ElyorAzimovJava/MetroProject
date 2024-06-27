package com.example.test.dto.result;

import com.example.test.enums.ResultType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultResponseForStatistics {
    private String description;
    private ResultType resultType;
    private Integer startPercentage;
    private Integer endPercentage;
    private Integer count;
    private Double percentage;
}
