package com.example.test.mapper.testSettings;

import com.example.test.dto.position.PositionResponseDto;
import com.example.test.dto.settingTest.OptionalTestRequestDto;
import com.example.test.dto.settingTest.OptionalTestResponseDto;
import com.example.test.exceptions.DataNotFoundException;
import com.example.test.module.OptionalTest;
import com.example.test.module.Position;
import com.example.test.repository.OptionalTestRepository;
import com.example.test.repository.PositionRepository;
import com.example.test.util.Variables;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptionalTestMapper {
    private final OptionalTestRepository optionalTestRepository;
    private final ModelMapper modelMapper;
    public OptionalTest mapToEntity(OptionalTestRequestDto requestDto){
        OptionalTest optionalTest = optionalTestRepository.findById(requestDto.getId())
                .orElseThrow(() -> new DataNotFoundException(Variables.NOT_FOUND.formatted(requestDto.getId() + " id li optionTest")));
                optionalTest.setCount(requestDto.getCount());
                optionalTest.getPosition().setName(requestDto.getName());
                return optionalTest;
    }
    public OptionalTestResponseDto mapToResponse(OptionalTest entity){
        OptionalTestResponseDto optionalTestResponseDto = new OptionalTestResponseDto();
        PositionResponseDto positionResponseDto = mapToPositionResponse(entity.getPosition());
        optionalTestResponseDto.setPositionResponseDto(positionResponseDto);
        optionalTestResponseDto.setCount(entity.getCount());
        optionalTestResponseDto.setId(entity.getId());
        return optionalTestResponseDto;
    }
    private PositionResponseDto mapToPositionResponse(Position position){
        PositionResponseDto map = modelMapper.map(position, PositionResponseDto.class);
        if(position.getDepartment() != null){
            map.setDepartmentId(position.getDepartment().getId());
            map.setDepartmentName(position.getDepartment().getName());
        }
        return map;
    }


}
