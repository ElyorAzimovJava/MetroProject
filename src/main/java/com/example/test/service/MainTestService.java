package com.example.test.service;

import com.example.test.dto.test.MainTestRequestDto;
import com.example.test.dto.test.MainTestResponse;
import com.example.test.dto.test.NewTestRequestDto;
import com.example.test.dto.test.TestResponseDto;
import com.example.test.exceptions.DataAlreadyExists;
import com.example.test.exceptions.DataNotFoundException;
import com.example.test.exceptions.InputException;
import com.example.test.module.MainTest;
import com.example.test.module.Position;
import com.example.test.module.Test;
import com.example.test.repository.MainTestRepository;
import com.example.test.repository.PositionRepository;
import com.example.test.repository.TestRepository;
import com.example.test.util.Variables;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainTestService {
    private final MainTestRepository mainTestRepository;
    private final ModelMapper modelMapper;
    private final TestRepository testRepository;
    private final ObjectMapper objectMapper;
    private final PositionRepository positionRepository;

    public void save(MainTestRequestDto requestDto) {
        System.out.println("requestDto = " + requestDto);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(requestDto.getFile().getInputStream()))) {
            String fileContent = reader.lines().collect(Collectors.joining("\n"));

            System.out.println("Fayl ichidagi malumotlar: " + fileContent);

            List<Test> tests = objectMapper.readValue(fileContent, new TypeReference<List<Test>>() {
            });
            System.out.println("tests = " + tests);
            MainTest mainTest = null;
            if (mainTestRepository.existsByName(requestDto.getName())) throw new DataAlreadyExists(Variables.ALL_READY_EXISTS.formatted("Test to'plam nomi"));

            if (!positionRepository.existsByNameAndIsDeletedFalse(requestDto.getPositionName())) {
                throw   new DataNotFoundException("Lavozim");
            }
            Optional<MainTest> byPositionName = mainTestRepository.findByPositionName(requestDto.getPositionName());
            Integer lastCount = testRepository.getLastCount();
            if(lastCount == null) lastCount=0;

            if(byPositionName.isPresent()){
                mainTest = byPositionName.get();
                mainTest.getTests().addAll(tests);
                mainTest.setUpdatedDate(LocalDateTime.now());
                for (Test test : tests) {
                    lastCount++;
                    test.setCode(lastCount);
                    test.setMainTest(byPositionName.get());
                }
            }
            else {
                mainTest = new MainTest(requestDto.getName(),tests,requestDto.getPositionName());
                for (Test test : tests) {
                    lastCount++;
                    test.setCode(lastCount);
                    test.setMainTest(mainTest);
                }
            }
             mainTestRepository.save(mainTest);
        } catch (IOException e) {
            throw new InputException("Fileni o'qishda xatolik. Iltimos fileni tekshiring");
        }
    }

    public List<MainTestResponse> allMainTests() {
        List<MainTest> all = mainTestRepository.findAll();
       return all.stream()
                .map(this::mapToMainTestResponse).collect(Collectors.toList());
    }



    private  TestResponseDto mapToTestResponse(Test test){
        return modelMapper.map(test,TestResponseDto.class);
    }

    public void addToMainTest(UUID mainTestId, NewTestRequestDto requestDto) {
        MainTest mainTest = mainTestRepository.findById(mainTestId)
                .orElseThrow(() -> new DataNotFoundException(Variables.NOT_FOUND.formatted("Testlar to'plami")));

        Test newTest = modelMapper.map(requestDto, Test.class);
        mainTest.getTests().add(newTest);
        mainTestRepository.save(mainTest);
    }
    public TestResponseDto updateTest(UUID testId,NewTestRequestDto requestDto){
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new DataNotFoundException(Variables.NOT_FOUND.formatted("Test")));
       test.setTitle(requestDto.getTitle());
       test.setVariantFirst(requestDto.getVariantFirst());
       test.setVariantSecond(requestDto.getVariantSecond());
       test.setVariantThird(requestDto.getVariantThird());
       test.setVariantFourth(requestDto.getVariantFourth());
       test.setVariantTrue(requestDto.getVariantTrue());
       return mapToTestResponse(testRepository.save(test));
    }

    public MainTestResponse updateMainTest(UUID id, MainTestRequestDto updateDto) {
        MainTest mainTest = mainTestRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(Variables.NOT_FOUND.formatted("Test to'plami")));
        Position position = positionRepository.findByName(updateDto.getPositionName())
                .orElseThrow(() -> new DataNotFoundException("Lavozim"));
        if(!mainTest.getPositionName().equals(updateDto.getPositionName())){ mainTest.setPositionName(position.getName());}

            mainTest.setUpdatedDate(LocalDateTime.now());
            mainTest.setName(updateDto.getName());
      return mapToMainTestResponse(mainTestRepository.save(mainTest));

    }
    private MainTestResponse mapToMainTestResponse(MainTest mainTest) {
        return new MainTestResponse(mainTest.getId(),mainTest.getName(), mainTest.getPositionName(), new ArrayList<>()
        );
    }

    public void deleteTestById(UUID testId) {
        if (testRepository.existsById(testId)) testRepository.deleteById(testId);
        throw new DataNotFoundException(Variables.NOT_FOUND.formatted("Test"));
    }

    public List<TestResponseDto> getMainTestTests(UUID mainTestId) {
        MainTest mainTest = mainTestRepository.findById(mainTestId)
                .orElseThrow(() -> new DataNotFoundException(Variables.NOT_FOUND.formatted("Main test")));
        return mainTest.getTests()
                .stream()
                .map(this::mapToTestResponse)
                .collect(Collectors.toList());
    }
}
