package com.example.test.service;

import com.example.test.dto.position.PositionResponseDto;
import com.example.test.dto.testter.TesterResponse;
import com.example.test.dto.testter.TesterUpdateDto;
import com.example.test.dto.userTestCollection.GenerateTestResponseDto;
import com.example.test.dto.userTestCollection.UserTestCollectionResponseDto;
import com.example.test.exceptions.DataAlreadyExists;
import com.example.test.exceptions.DataNotFoundException;
import com.example.test.module.*;
import com.example.test.repository.*;
import com.example.test.util.Variables;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TesterService {
    private final TesterRepository testerRepository;
    private final ModelMapper modelMapper;
    private final PositionRepository positionRepository;
    private final EntityManager entityManager;
    private final UserTestCollectionRepository userTestCollectionRepository;
    private final TesterHistoryRepository testerHistoryRepository;
    private final TestSettingRepository testSettingRepository;




    public List<UserTestCollection> getUserTests(UUID testerHistoryId){
        return userTestCollectionRepository.getUserTestCollectionByTesterHistoryId(testerHistoryId);
    }
    public GenerateTestResponseDto startTest(UUID testerId) {
        Tester tester = testerRepository.findById(testerId)
                .orElseThrow(() -> new DataNotFoundException(Variables.NOT_FOUND.formatted("Tester ")));
        TestSettings testSetting = testSettingRepository.getTestSetting();
        List<Test> getMainTest = getRandomTests(testSetting.getMainPositionTestCount(), tester.getPosition().getName());
        if(getMainTest.size() < testSetting.getMainPositionTestCount()) throw new DataNotFoundException(Variables.NOT_FOUND.formatted(tester.getPosition().getName() + " mos yetarlicha test "));

       getMainTest.addAll( testSetting.getOptionalTests()
                .stream()
                .filter(optionalTest -> optionalTest.getCount() > 0)
                .flatMap(optionalTest -> getRandomTests(optionalTest.getCount(), optionalTest.getPosition().getName()).stream())
                .toList());

            TesterHistory testerHistory = TesterHistory.builder()
                .tester(tester)
                .testStartDate(LocalDateTime.now())
                .build();
        TesterHistory saveTesterHistory = testerHistoryRepository.save(testerHistory);
        List<UserTestCollection> userTestCollections = mapToResponse(getMainTest,saveTesterHistory);
        userTestCollectionRepository.saveAll(userTestCollections);




return new GenerateTestResponseDto(testSettingRepository.getTestSetting().getTimeOfTest(),saveTesterHistory.getId(),
         testerId,
         userTestCollections.stream()
                .map(this::mapToCollectionResponse)
                .toList()
);

    }

    public TesterResponse getTesterByIdNumber(String idNumber) {
       return  mapToTesterResponse(testerRepository.findByIdNumber(idNumber)
                .orElseThrow(() -> new DataNotFoundException(Variables.NOT_FOUND.formatted("Tester"))));


    }



    public void deleteTester(UUID id) {
        if (testerRepository.existsById(id)){
            testerRepository.deleteById(id);
        }

        else throw new DataNotFoundException(Variables.NOT_FOUND.formatted("Tester"));
    }

    public void updateTester(UUID testerId, TesterUpdateDto testerUpdateDto) {
        Tester tester = testerRepository.findById(testerId)
                .orElseThrow(() -> new DataNotFoundException(Variables.NOT_FOUND.formatted("Tester")));

         if(!Objects.equals(tester.getIdNumber(),testerUpdateDto.getIdNumber()) && testerRepository.existsByIdNumber(testerUpdateDto.getIdNumber() )){
             throw new DataAlreadyExists(Variables.ALL_READY_EXISTS.formatted("ID raqam"));
         }


        if(!tester.getPosition().getId().equals(testerUpdateDto.getPositionId())){
            Position position = positionRepository.findByIdAndIsDeletedFalse(testerUpdateDto.getPositionId())
                    .orElseThrow(() -> new DataNotFoundException(Variables.NOT_FOUND.formatted("Lavozim")));
            tester.setPosition(position);
        }
        tester.setFirstName(testerUpdateDto.getFirstName());
        tester.setMiddleName(testerUpdateDto.getMiddleName());
        tester.setLastName(testerUpdateDto.getLastName());
        tester.setIdNumber(testerUpdateDto.getIdNumber());
        tester.setUpdatedDate(LocalDateTime.now());
        testerRepository.save(tester);
    }
    public List<TesterResponse> getAllTester() {
        List<Tester> all = testerRepository.findAll();
        return all.stream().map(this::mapToTesterResponse).toList();

    }

    private UserTestCollectionResponseDto mapToCollectionResponse(UserTestCollection collection){

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
    private TesterResponse mapToTesterResponse(Tester tester){
        TesterResponse response = modelMapper.map(tester, TesterResponse.class);
        response.setPosition(mapToPositionResponse(tester.getPosition()));
        return response;
    }
    private PositionResponseDto mapToPositionResponse(Position position){
        PositionResponseDto map = modelMapper.map(position, PositionResponseDto.class);
        if(position.getDepartment() != null){
            map.setDepartmentId(position.getDepartment().getId());
            map.setDepartmentName(position.getDepartment().getName());
        }
        return map;
    }
    private List<UserTestCollection> mapToResponse(List<Test> tests, TesterHistory testerHistory){
        ArrayList<UserTestCollection> testResponseDtos = new ArrayList<>();
        Random random = new Random();
        int testNumber = 1;
        for (Test test : tests) {
            UserTestCollection testResponseDto = new UserTestCollection();
            testResponseDto.setId(test.getId());
            testResponseDto.setTitle(test.getTitle());
            testResponseDto.setVariantTrue(test.getVariantTrue());
            testResponseDto.setTestId(test.getId());
            testResponseDto.setTesterHistory(testerHistory);
            testResponseDto.setTestNumber(testNumber);
            if(testNumber ==1){
                testResponseDto.setFirstTest(true);
                testResponseDto.setLastTest(false);

            }
            else if(testNumber == tests.size()){
                testResponseDto.setLastTest(true);
                testResponseDto.setFirstTest(false);
            }
            else {
                testResponseDto.setLastTest(false);
                testResponseDto.setFirstTest(false);
            }
            int randomNumber = random.nextInt(1,4);

            if(randomNumber == 1){
                testResponseDto.setVariantFirst(test.getVariantFourth());
                testResponseDto.setVariantSecond(test.getVariantThird());
                testResponseDto.setVariantThird(test.getVariantSecond());
                testResponseDto.setVariantFourth(test.getVariantFirst());
            }
            else if(randomNumber ==2){
                testResponseDto.setVariantFirst(test.getVariantSecond());
                testResponseDto.setVariantSecond(test.getVariantFirst());
                testResponseDto.setVariantThird(test.getVariantFourth());
                testResponseDto.setVariantFourth(test.getVariantThird());
            }else{
                testResponseDto.setVariantFirst(test.getVariantThird());
                testResponseDto.setVariantSecond(test.getVariantFourth());
                testResponseDto.setVariantThird(test.getVariantFirst());
                testResponseDto.setVariantFourth(test.getVariantSecond());
            }
            testNumber++;
            testResponseDtos.add(testResponseDto);

        }
        return testResponseDtos;
    }
    private Integer getTestCount(Integer totalTest, Integer percentage){
        if(percentage == 0) return  0;
        return totalTest * percentage / 100;
    }
    private List<Test> getResultList(String positionUser, String positionOne, String positionTwo,Integer perUser, Integer perOne, Integer perTwo){
        List<Test> randomTests = getRandomTests(perUser, positionUser);
        randomTests.addAll(getRandomTests(perOne,positionOne));
        randomTests.addAll(getRandomTests(perTwo,positionTwo));
        return randomTests;
    }
    private List<Test> getRandomTests(Integer limit, String positionName){
        TypedQuery<Test> query = entityManager.createQuery("SELECT t" +
                " FROM Test  t where t.mainTest.positionName =: positionName ORDER BY random()",Test.class);
        query.setMaxResults(limit);
        query.setParameter("positionName",positionName);
        List<Test> resultList = query.getResultList();
        return resultList;
    }


}
