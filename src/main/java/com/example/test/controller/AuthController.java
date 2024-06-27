package com.example.test.controller;

import com.example.test.dto.auth.ApiResponse;
import com.example.test.dto.auth.LoginDto;
import com.example.test.dto.testter.TesterRequestDto;
import com.example.test.dto.testter.TesterResponse;
import com.example.test.module.BaseEntity;
import com.example.test.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<?> login(@Valid @RequestBody LoginDto loginDto){
        return   authService.login(loginDto);
    }
    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public TesterResponse register(@Valid @RequestBody TesterRequestDto requestDto){
       return authService.registerTester(requestDto);
    }
}
