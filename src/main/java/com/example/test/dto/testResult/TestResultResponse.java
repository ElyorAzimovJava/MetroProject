package com.example.test.dto.testResult;

import com.example.test.dto.department.DepartmentResponseDto;
import com.example.test.dto.position.PositionResponseDto;
import com.example.test.module.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TestResultResponse {
    private String name;
    private String middleName;
    private String lastName;
    private LocalDateTime testStartDate;
    private LocalDateTime testEndDate;
    private Integer totalTestCount;
    private Integer correctTestCount;
    private Integer percentage;
    private String positionName;
    private String description;
}
