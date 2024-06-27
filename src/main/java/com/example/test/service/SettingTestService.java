package com.example.test.service;

import com.example.test.dto.settingTest.SettingTestRequestDto;
import com.example.test.dto.settingTest.TestSettingResponseDto;
import com.example.test.mapper.testSettings.TestSettingMapper;
import com.example.test.module.TestSettings;
import com.example.test.repository.OptionalTestRepository;
import com.example.test.repository.TestSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettingTestService {
    private final TestSettingRepository testSettingRepository;
    private final TestSettingMapper testSettingMapper;

    public void updateSettingTest(SettingTestRequestDto settingTestRequestDto){
        TestSettings testSetting = testSettingRepository.getTestSetting();
        testSettingMapper.mapToEntity(testSetting,settingTestRequestDto);
        testSettingRepository.save(testSetting);
    }

    public TestSettingResponseDto getTestSetting() {
        TestSettings testSetting = testSettingRepository.getTestSetting();
        return testSettingMapper.mapToResponse(testSetting);
    }

    public Integer getTimeOfTest() {
        return testSettingRepository.getTestSetting().getTimeOfTest();
    }
}
