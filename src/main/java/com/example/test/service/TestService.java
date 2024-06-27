package com.example.test.service;

import com.example.test.repository.TesterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TesterRepository testerRepository;


}
