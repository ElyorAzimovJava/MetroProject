package com.example.test.service;

import com.example.test.repository.TestResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TestResultService {
    private final TestResultRepository testResultRepository;
}
