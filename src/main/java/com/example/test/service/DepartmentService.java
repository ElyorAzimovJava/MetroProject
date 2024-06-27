package com.example.test.service;

import com.example.test.dto.CustomPagination;
import com.example.test.dto.department.DepartmentCreateDto;
import com.example.test.dto.department.DepartmentResponseDto;
import com.example.test.dto.department.DepartmentUpdateDto;
import com.example.test.dto.position.PositionResponseDto;
import com.example.test.exceptions.DataAlreadyExists;
import com.example.test.exceptions.DataNotFoundException;
import com.example.test.module.Department;
import com.example.test.module.Position;
import com.example.test.repository.DepartmentRepository;
import com.example.test.util.Variables;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    public void addDepartment(DepartmentCreateDto departmentRequestDto){
        Boolean existByName = departmentRepository.existsByNameAndIsDeletedFalse(departmentRequestDto.getName());
        if(existByName)
            throw new DataAlreadyExists(Variables.ALL_READY_EXISTS.formatted("Department name "));
        Department newDepartment = Department.builder()
                .name(departmentRequestDto.getName())
                .isDeleted(false)
                .build();
        departmentRepository.save(newDepartment);

    }
    public DepartmentResponseDto updateDepartmentName(DepartmentUpdateDto updateDto){
        Department department = departmentRepository.findById(updateDto.getDepartmentId())
                .orElseThrow(() -> new DataNotFoundException(Variables.NOT_FOUND.formatted("Department ")));
        Boolean existByName = departmentRepository.existsByNameAndIsDeletedFalse(updateDto.getNewName());
        if(existByName)
            throw new DataAlreadyExists(Variables.ALL_READY_EXISTS.formatted("Department name "));
         department.setName(updateDto.getNewName());
         department.setUpdatedDate(LocalDateTime.now());
         Department save = departmentRepository.save(department);
        return mapToDepartmentResponse(save);

    }
    public List<DepartmentResponseDto> departmentList(){
        List<Department> all = departmentRepository.findByIsDeletedFalse();
        System.out.println("all = " + all);
        return   all.stream()
                  .map(this::mapToDepartmentResponse)
                  .collect(Collectors.toList());
    }
    private DepartmentResponseDto mapToDepartmentResponse(Department department){
        DepartmentResponseDto map = modelMapper.map(department, DepartmentResponseDto.class);
        map.setCreatedDate(LocalDate.of(department.getCreatedDate().getYear(), department.getCreatedDate().getMonth(),department.getCreatedDate().getDayOfMonth() ));
        map.setUpdatedDate(LocalDate.of(department.getUpdatedDate().getYear(), department.getUpdatedDate().getMonth(),department.getUpdatedDate().getDayOfMonth() ));
        map.setPositions(mapToPositionResponse(department.getPositions(),department.getId()));
        return map;
    }
    private List<PositionResponseDto> mapToPositionResponse(List<Position> position, UUID departmentId){
        List<PositionResponseDto> map = modelMapper.map(position, new TypeToken<List<PositionResponseDto>>() {
        }.getType());
        for (PositionResponseDto positionResponseDto : map) {
            positionResponseDto.setDepartmentId(departmentId);
        }
        System.out.println("map = " + map);
        return map;

    }

    public CustomPagination<DepartmentResponseDto> getDepartmentByPagination(Integer page) {
        boolean lastPage = false;
        boolean firstPage = page == 0;

        Integer allActiveDepartment = departmentRepository.getDepartmentActiveDepartmentCount();
        if(allActiveDepartment == 0) throw new DataNotFoundException(Variables.NOT_FOUND.formatted("Department"));
        Integer elementCount = Variables.DEPARTMENT_PAGE_SIZE;
        Integer totalPages = allActiveDepartment / elementCount;
        Pageable pageable =  PageRequest.of(page,elementCount);
        List<DepartmentResponseDto> data = departmentRepository.findAllByIsDeletedFalseOrderByCreatedDateDesc(pageable)
                .stream()
                .map(this::mapToDepartmentResponse)
                .collect(Collectors.toList());
        if(page * elementCount >= allActiveDepartment - elementCount ) lastPage = true;
        return new CustomPagination<>(allActiveDepartment,page,data.size(),totalPages,data,firstPage,lastPage);
    }

    public DepartmentResponseDto getById(UUID id) {
        return mapToDepartmentResponse(departmentRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new DataNotFoundException(Variables.NOT_FOUND.formatted("Department"))));
    }
}
