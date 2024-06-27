package com.example.test.service;

import com.example.test.dto.settingTest.OptionalTestCreateDto;
import com.example.test.exceptions.DataAlreadyExists;
import com.example.test.module.OptionalTest;
import com.example.test.module.Position;
import com.example.test.module.TestSettings;
import com.example.test.repository.OptionalTestRepository;
import com.example.test.repository.PositionRepository;
import com.example.test.repository.TestSettingRepository;
import com.example.test.util.Variables;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptionalTestService {
    private final OptionalTestRepository optionalTestRepository;
    private final PositionRepository positionRepository;
    private final TestSettingRepository testSettingRepository;
    public  void addOptionalPosition(OptionalTestCreateDto createDto){
        TestSettings testSetting = testSettingRepository.getTestSetting();
        if (positionRepository.existsByNameAndIsDeletedFalse(createDto.getName()))
            throw new DataAlreadyExists(Variables.ALL_READY_EXISTS.formatted(createDto.getName()));
        Position position = new Position(createDto.getName(), null, false, false);
        positionRepository.save(position);
        OptionalTest newOptionalTest = new OptionalTest(position, testSetting, 0);
        optionalTestRepository.save(newOptionalTest);

    }
}
