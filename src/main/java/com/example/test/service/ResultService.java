package com.example.test.service;

import com.example.test.repository.ResultRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResultService {
    private final ResultRepository repository;
}
