package com.example.test.service;

import com.example.test.dto.testResult.TestResultResponse;
import com.example.test.dto.testerHistory.OneTestHistory;
import com.example.test.dto.testerHistory.TesterHistoryResponse;
import com.example.test.dto.testter.TesterResponse;
import com.example.test.dto.userTestCollection.UserTestCollectionRequestDto;
import com.example.test.dto.userTestCollection.UserTestCollectionResponseDto;
import com.example.test.exceptions.DataNotFoundException;
import com.example.test.module.*;
import com.example.test.repository.*;
import com.example.test.util.Variables;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserTestCollectionService {
    private final UserTestCollectionRepository repository;
    private final ResultRepository resultRepository;
    private final TestResultRepository testResultRepository;
    private final TesterRepository testerRepository;
    private final TesterHistoryRepository testerHistoryRepository;
    private final ModelMapper modelMapper;




    public TestResultResponse getUserResult(UUID testerHistoryId, List<UserTestCollectionRequestDto> tests){
        TesterHistory testerHistory = testerHistoryRepository.findById(testerHistoryId)
                .orElseThrow(() -> new DataNotFoundException(Variables.NOT_FOUND.formatted("Testernig tarixi")));
        List<UserTestCollection> collections = repository.getUserTestCollectionByTesterHistoryId(testerHistoryId);

        for (UserTestCollectionRequestDto test : tests) {
            for(UserTestCollection collection : collections){
                if(test.getSelectVariant() != null &&test.getTestNumber().equals(collection.getTestNumber()) ){
                    collection.setSelectVariant(test.getSelectVariant());
                    if(collection.getVariantTrue().equals(test.getSelectVariant())){
                        collection.setTrueAnswer(true);
                    }
                }
            }
        }
        repository.saveAll(collections);


        Tester tester = testerHistory.getTester();
        Integer testerTotalTestCount = repository.getTesterTotalTestCount(testerHistoryId);
        Integer userCorrectAnswers = repository.getUserCorrectAnswers(testerHistoryId);
        Integer percentage = userCorrectAnswers * 100/testerTotalTestCount;
        testerHistory.setTestEndDate(LocalDateTime.now());
        testerHistory.setUpdatedDate(LocalDateTime.now());
        testerHistoryRepository.save(testerHistory);
        Result result = resultRepository.getCorrectResult(percentage)
                .orElseThrow(() -> new RuntimeException("Mos bo'lgan Result class topilmadi"));
        TesterResult testerResult = new TesterResult( testerHistory,testerTotalTestCount, userCorrectAnswers, percentage, result);
        testResultRepository.save(testerResult);
        return mapToTestResult(testerResult,tester);
    }
    private TestResultResponse mapToTestResult(TesterResult testerResult, Tester tester){
        return new TestResultResponse(
                tester.getFirstName(),
                tester.getMiddleName(),
                tester.getLastName(),
                testerResult.getTesterHistory().getTestStartDate(),
                testerResult.getTesterHistory().getTestStartDate(),
                testerResult.getTotalTest(),
                testerResult.getCorrectTest(),
                testerResult.getPercentage(),
                tester.getPosition().getName(),
                testerResult.getResult().getDescription()
        );
    }
    private UserTestCollectionResponseDto mapToCollectionResponse(UserTestCollection collection,UUID testerId){

        return   new UserTestCollectionResponseDto(
                collection.getId(),
                collection.getTitle(),
                collection.getVariantFirst(),
                collection.getVariantSecond(),
                collection.getVariantThird(),
                collection.getVariantFourth(),
                collection.getTestNumber(),
                collection.isLastTest(),
                collection.isFirstTest()
        );
    }

    public List<TestResultResponse> getTesterResults(UUID testerId) {
        Tester tester = testerRepository.findById(testerId)
                .orElseThrow(() -> new DataNotFoundException(Variables.NOT_FOUND.formatted("Tester")));

        List<TesterResult> byTesterId = testResultRepository.getByTesterHistoryTesterId(testerId);
       return byTesterId.stream()
                .map(testerResult -> mapToTestResult(testerResult,tester))
                .collect(Collectors.toList());
    }

    public TesterHistoryResponse getTesterHistory(UUID resultId) {
        TesterResult result = testResultRepository.findById(resultId)
                .orElseThrow(() -> new DataNotFoundException(Variables.NOT_FOUND.formatted("Result ")));
        TesterHistory testerHistory = result.getTesterHistory();
        return mapToTesterHistory(testerHistory);
    }
    private TesterHistoryResponse mapToTesterHistory(TesterHistory testerHistory){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Variables.LOCAL_DATE_TIME_FORMAT);
        String startDate = testerHistory.getTestStartDate().format(formatter);
        String endDate = testerHistory.getTestEndDate().format(formatter);

        return   new TesterHistoryResponse(
               mapToTesterResponse(testerHistory.getTester()),
                testerHistory.getTestCollections()
                        .stream()
                        .map(this::mapToOneHistory)
                        .collect(Collectors.toList()),
                startDate,
                endDate
        );
       // TODO: 25/01/2024
    }
    private TesterResponse mapToTesterResponse(Tester tester){
        return modelMapper.map(tester, TesterResponse.class);
    }
    private OneTestHistory mapToOneHistory(UserTestCollection collection){
      return   new OneTestHistory(collection.getId(),collection.getTitle(),collection.getTestNumber(),collection.isTrueAnswer(),collection.getSelectVariant());
    }

}
