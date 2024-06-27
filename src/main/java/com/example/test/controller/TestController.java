package com.example.test.controller;

import com.example.test.dto.test.MainTestRequestDto;
import com.example.test.dto.test.MainTestResponse;
import com.example.test.dto.test.NewTestRequestDto;
import com.example.test.dto.test.TestResponseDto;
import com.example.test.service.MainTestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final MainTestService mainTestService;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PostMapping(value = "/addTest",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addTest(@Valid @ModelAttribute MainTestRequestDto requestDto){
        mainTestService.save(requestDto);
    }
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @GetMapping("/getAllMainTest")
    public List<MainTestResponse> getAllMainTests(){
        return mainTestService.allMainTests();
    }
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PostMapping("/addNewTest/{mainTestId}")
    public void  addTestToMainTest(@PathVariable UUID mainTestId, @Valid @RequestBody NewTestRequestDto requestDto){
         mainTestService.addToMainTest(mainTestId,requestDto);
    }
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PutMapping("/updateTest/{testId}")
    public TestResponseDto updateTest(@PathVariable UUID testId,@Valid @RequestBody NewTestRequestDto requestDto){
        return mainTestService.updateTest(testId,requestDto);
    }
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PutMapping("/updateMainTest/{id}")
    public MainTestResponse updateMainTest(@PathVariable UUID id, @RequestBody MainTestRequestDto updateDto){
      return   mainTestService.updateMainTest(id,updateDto);
    }
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @DeleteMapping("/deleteTest/{testId}")
    public void deleteTestById(@PathVariable UUID testId){
         mainTestService.deleteTestById(testId);
    }

    @GetMapping("/getTests/{mainTestId}")
    public List<TestResponseDto> getMainTestTests(@PathVariable UUID mainTestId){
      return   mainTestService.getMainTestTests(mainTestId);
    }

}
