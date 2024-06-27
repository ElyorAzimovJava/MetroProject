package com.example.test.dto.settingTest;

import com.example.test.module.OptionalTest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SettingTestRequestDto {
    private Integer totalTestCount;
    private Integer mainTestCount;
    private Integer timeOfTest;
    private List<OptionalTestRequestDto> optionalTests;
}
