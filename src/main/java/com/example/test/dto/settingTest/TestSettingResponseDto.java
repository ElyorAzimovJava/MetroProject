package com.example.test.dto.settingTest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestSettingResponseDto {
    private Integer totalTestCount;
    private Integer mainTestCount;
    private Integer timeOfTest;
    private List<OptionalTestResponseDto> optionalTests;
}
