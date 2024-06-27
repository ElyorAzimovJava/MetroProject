package com.example.test.mapper.testSettings;

import com.example.test.dto.position.PositionResponseDto;
import com.example.test.dto.settingTest.SettingTestRequestDto;
import com.example.test.dto.settingTest.TestSettingResponseDto;
import com.example.test.module.OptionalTest;
import com.example.test.module.Position;
import com.example.test.module.TestSettings;
import com.example.test.repository.OptionalTestRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestSettingMapper {
    private final OptionalTestMapper optionalTestMapper;
    private final OptionalTestRepository optionalTestRepository;
    public void mapToEntity(TestSettings testSettings, SettingTestRequestDto requestDto){
        List<OptionalTest> optionalTests = requestDto.getOptionalTests()
                .stream()
                .map(this.optionalTestMapper::mapToEntity)
                .toList();
       optionalTestRepository.saveAll(optionalTests);
        testSettings.setTimeOfTest(requestDto.getTimeOfTest());
        testSettings.setTotalTestCount(requestDto.getTotalTestCount());
        testSettings.setMainPositionTestCount(requestDto.getMainTestCount());
    }
    public TestSettingResponseDto mapToResponse(TestSettings entity){
        TestSettingResponseDto testSettingResponseDto = new TestSettingResponseDto();
        testSettingResponseDto.setMainTestCount(entity.getMainPositionTestCount());
        testSettingResponseDto.setTotalTestCount(entity.getTotalTestCount());
        testSettingResponseDto.setTimeOfTest(entity.getTimeOfTest());
        testSettingResponseDto.setOptionalTests(entity.getOptionalTests()
                .stream()
                .map(this.optionalTestMapper::mapToResponse)
                .toList());
        return testSettingResponseDto;

    }

}
