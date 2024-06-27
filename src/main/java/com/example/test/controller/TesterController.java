package com.example.test.controller;

import com.example.test.dto.CustomPagination;
import com.example.test.dto.statistics.GetTestersRequest;
import com.example.test.dto.testResult.TestResultResponse;
import com.example.test.dto.testerHistory.TesterHistoryResponse;
import com.example.test.dto.testter.TesterResponse;
import com.example.test.dto.testter.TesterUpdateDto;
import com.example.test.dto.userTestCollection.GenerateTestResponseDto;
import com.example.test.dto.userTestCollection.UserTestCollectionRequestDto;
import com.example.test.dto.userTestCollection.UserTestCollectionResponseDto;
import com.example.test.module.UserTestCollection;
import com.example.test.service.TesterService;
import com.example.test.service.UserTestCollectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tester")
@RequiredArgsConstructor
public class TesterController {
    private final TesterService testerService;
    private final UserTestCollectionService service;
    @PostMapping("/finishTest/{testerHistoryId}")
    @PreAuthorize("hasRole('TESTER')")
    public TestResultResponse finishTest(@PathVariable UUID testerHistoryId, @Valid List<UserTestCollectionRequestDto> tests){
       return service.getUserResult(testerHistoryId,tests);
    }
    @GetMapping("/startTest/{testerId}")
    @PreAuthorize("hasRole('TESTER')")
    public GenerateTestResponseDto startTest(@PathVariable UUID testerId){
        return testerService.startTest(testerId);
    }
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN','DIRECTOR')")
    @GetMapping("/getTesterByIdNumber/{idNumber}")
    public TesterResponse getTesterByIdNumber(@PathVariable String idNumber){
        return testerService.getTesterByIdNumber(idNumber);
    }
    @GetMapping("/getTesterTest/{id}")
    public List<UserTestCollection> getUserTest(@PathVariable UUID id){
            return testerService.getUserTests(id);
    }
    @GetMapping("/tester/history/{testerId}")
    public List<TestResultResponse> getTesterResults(@PathVariable UUID testerId){
        return service.getTesterResults(testerId);
    }
    @GetMapping("/testerHistory/{resultId}")
    public TesterHistoryResponse testerHistory(@PathVariable UUID resultId){
        return service.getTesterHistory(resultId);
    }
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN','DIRECTOR')")
    @GetMapping("/all")
    public List<TesterResponse>getTesterForMonitoring(){
        return testerService.getAllTester();
    }
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/deleteTester/{testerId}")
    public void deleteTester(@PathVariable UUID testerId){
        testerService.deleteTester(testerId);
    }

    @PutMapping("/update/{testerId}")
    public void updateTesterDate(@PathVariable UUID testerId, @Valid @RequestBody TesterUpdateDto testerUpdateDto){
        testerService.updateTester(testerId,testerUpdateDto);
    }

}
