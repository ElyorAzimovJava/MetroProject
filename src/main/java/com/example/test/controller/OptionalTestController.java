package com.example.test.controller;

import com.example.test.dto.settingTest.OptionalTestCreateDto;
import com.example.test.service.OptionalTestService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController("/optionalTest")
@RequiredArgsConstructor
public class OptionalTestController {
    private final OptionalTestService optionalTestService;
    @PostMapping("/add")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void add(@Valid  @RequestBody OptionalTestCreateDto createDto){
        optionalTestService.addOptionalPosition(createDto);
    }
}
