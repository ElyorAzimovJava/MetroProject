package com.example.test.controller;

import com.example.test.dto.settingTest.SettingTestRequestDto;
import com.example.test.dto.settingTest.TestSettingResponseDto;
import com.example.test.mapper.testSettings.TestSettingMapper;
import com.example.test.module.TestSettings;
import com.example.test.service.SettingTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/settingTest")
@RequiredArgsConstructor
public class TestSettingController {

    private final SettingTestService settingTestService;
    @PutMapping("/updateTestSetting")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
   public void updateTestSetting(@RequestBody SettingTestRequestDto requestDto){
    settingTestService.updateSettingTest(requestDto);
  }
  @GetMapping("/getTime")
  public Integer getTimeOfTest(){
        return settingTestService.getTimeOfTest();
  }
  @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN','TESTER')")
  @GetMapping("/getSettingTest")
  public TestSettingResponseDto getSettingTest(){
      return settingTestService.getTestSetting();
  }

}
