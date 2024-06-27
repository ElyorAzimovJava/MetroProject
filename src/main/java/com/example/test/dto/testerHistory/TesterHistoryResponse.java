package com.example.test.dto.testerHistory;

import com.example.test.dto.testResult.TestResultResponse;
import com.example.test.dto.testter.TesterResponse;
import com.example.test.dto.userTestCollection.UserTestCollectionResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TesterHistoryResponse {
    private TesterResponse testerResponse;
    private List<OneTestHistory> tests;
    private String testStartDate;
    private String testEndDate;
}
