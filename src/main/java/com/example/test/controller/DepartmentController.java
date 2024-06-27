package com.example.test.controller;

import com.example.test.dto.CustomPagination;
import com.example.test.dto.department.DepartmentCreateDto;
import com.example.test.dto.department.DepartmentResponseDto;
import com.example.test.dto.department.DepartmentUpdateDto;
import com.example.test.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;
    @GetMapping("/allDepartments")
    public List<DepartmentResponseDto> getAllDepartments(){
        return departmentService.departmentList();
    }
    @PostMapping("/addDepartment")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public void addDepartment(@Valid @RequestBody DepartmentCreateDto requestDto){
            departmentService.addDepartment(requestDto);
    }
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PatchMapping("/updateName")
    public DepartmentResponseDto departmentResponseDto(@Valid @RequestBody DepartmentUpdateDto updateDto){
           return departmentService.updateDepartmentName(updateDto);
    }
    @GetMapping("/allDepartments/{page}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    public CustomPagination<DepartmentResponseDto> getDepartmentByPagination(@PathVariable Integer page){
        return departmentService.getDepartmentByPagination(page);
    }
    @GetMapping("/getById/{id}")
    public DepartmentResponseDto getById(@PathVariable UUID id){
        return departmentService.getById(id);
    }


}
