package com.example.test.controller;

import com.example.test.dto.statistics.GenericStatisticsResponse;
import com.example.test.dto.statistics.StatisticsOfDepartments;
import com.example.test.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsService service;
    @GetMapping("/department")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN','DIRECTOR')")
    public List<StatisticsOfDepartments> getDepartmentsStatistics(){
       return service.statisticsOfDepartments();
    }
    @GetMapping("/position")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN','DIRECTOR')")
    public List<StatisticsOfDepartments> getPositionsStatistics(){
       return service.statisticsOfPositions();
    }
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN','DIRECTOR')")
    @GetMapping("/generic")
    public GenericStatisticsResponse genericStatistics(){
        return service.statisticsForGeneric();
    }

}
