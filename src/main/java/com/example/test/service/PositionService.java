package com.example.test.service;

import com.example.test.dto.CustomPagination;
import com.example.test.dto.position.PositionRequestDto;
import com.example.test.dto.position.PositionResponseDto;
import com.example.test.dto.position.PositionUpdateDto;
import com.example.test.exceptions.DataAlreadyExists;
import com.example.test.exceptions.DataNotFoundException;
import com.example.test.module.Department;
import com.example.test.module.Position;
import com.example.test.repository.DepartmentRepository;
import com.example.test.repository.PositionRepository;
import com.example.test.util.Variables;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PositionService {
    private final PositionRepository positionRepository;
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    public Optional<Position> findById(UUID position) {
        return positionRepository.findById(position);
    }
    public List<PositionResponseDto> getByDepartmentId(UUID departmentId){
        List<Position> byDepartmentId = positionRepository.getByDepartmentIdIAndIsDeletedFalse(departmentId);
        return byDepartmentId.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    public List<PositionResponseDto> getAll(){
       return positionRepository.getByPositionNameUnique()
                .stream()
                .map(this::mapToResponse)
                .distinct()
                .collect(Collectors.toList());
    }
    public PositionResponseDto  updatePositionName(UUID id, PositionUpdateDto updateDto ){
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Position"));
        Boolean existName = positionRepository.uniquePositionNameInDepartment(updateDto.getNewName(), position.getDepartment().getId());
        if(existName){
            throw new DataAlreadyExists(Variables.ALL_READY_EXISTS.formatted("Bunday lavozim nomi departmentda"));
        }
        position.setName(updateDto.getNewName());
        position.setUpdatedDate(LocalDateTime.now());
        Position updatePosition = positionRepository.save(position);
        return mapToResponse(updatePosition);
    }

    public void deleteById(UUID id) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(Variables.NOT_FOUND.formatted("Lavozim")));
        position.setIsDeleted(true);
        position.setUpdatedDate(LocalDateTime.now());
        positionRepository.save(position);
    }

    public CustomPagination<PositionResponseDto> getPositionByPagination(Integer page) {
        boolean lastPage = false;
        boolean firstPage = page == 0;
        Integer elementCount = Variables.DEPARTMENT_PAGE_SIZE;
        Integer allPositions = positionRepository.countAllByIsDeletedFalse();
        Integer totalPages = allPositions / elementCount;
        Pageable pageable = PageRequest.of(page,elementCount);
        if(page * elementCount >= allPositions - elementCount ) lastPage = true;
        List<Position> positions = positionRepository.getAllByIsDeletedFalse(pageable);
        return new CustomPagination<>(elementCount,page,positions.size(),totalPages,
                positions.stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList())
                        ,firstPage,lastPage);
    }


    public PositionResponseDto getById(UUID id) {
        return mapToResponse(positionRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new DataNotFoundException(Variables.NOT_FOUND.formatted("Lavozim"))));

    }

    public List<PositionResponseDto> getRealPositions() {
        return positionRepository.getByRealPositionNameUnique()
                .stream()
                .map(this::mapToResponse)
                .distinct()
                .collect(Collectors.toList());
    }
    private PositionResponseDto mapToResponse(Position position){
        PositionResponseDto map = modelMapper.map(position, PositionResponseDto.class);
        if(position.getDepartment() != null){
            map.setDepartmentId(position.getDepartment().getId());
            map.setDepartmentName(position.getDepartment().getName());
        }
        return map;
    }

    public List<PositionResponseDto> getGenericPositions() {
     return    positionRepository.getGenericPositions()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void addPositionToDepartment(UUID departmentId, PositionRequestDto requestDto) {
        Department department = departmentRepository.findByIdAndIsDeletedFalse(departmentId)
                .orElseThrow(() -> new DataNotFoundException(Variables.NOT_FOUND.formatted("Department")));

        Boolean exist = positionRepository.existsByNameAndDepartmentId(requestDto.getName(), departmentId);
        if(exist){
            throw new DataAlreadyExists(Variables.ALL_READY_EXISTS.formatted("Departmentda bunday nomli lavozim"));
        }
        Position position = new Position(requestDto.getName(), department, true, false);
        positionRepository.save(position);
        }

    }

