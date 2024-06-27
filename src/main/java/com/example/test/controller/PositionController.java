package com.example.test.controller;

import com.example.test.dto.CustomPagination;
import com.example.test.dto.position.PositionRequestDto;
import com.example.test.dto.position.PositionResponseDto;
import com.example.test.dto.position.PositionUpdateDto;
import com.example.test.module.Position;
import com.example.test.repository.PositionRepository;
import com.example.test.service.PositionService;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/position")
@RequiredArgsConstructor
public class PositionController {
    private final PositionService positionService;
    @GetMapping("/getPositionByDepartmentId")
    public List<PositionResponseDto> positions(@RequestParam UUID departmentId){
      return positionService.getByDepartmentId(departmentId);
    }
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @GetMapping
    List<PositionResponseDto> getAllPositions(){
        return positionService.getAll();
    }
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @GetMapping("/getRealTesterPosition")
    public List<PositionResponseDto> getRealPositions(){
            return positionService.getRealPositions();
    }
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PatchMapping("/updateName/{id}")
    public PositionResponseDto updatePosition(@PathVariable UUID id, @Valid @RequestBody PositionUpdateDto updateDto){
            return positionService.updatePositionName(id, updateDto);
    }
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void deletePositionById(@PathVariable UUID id){
        positionService.deleteById(id);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @GetMapping("/allPosition/{page}")
    public CustomPagination<PositionResponseDto> getPositionByPagination(@PathVariable Integer page){
       return positionService.getPositionByPagination(page);
    }
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @GetMapping("/getById/{id}")
    public PositionResponseDto getPositionById(@PathVariable UUID id){
        return positionService.getById(id);
    }
    @GetMapping("/getGenericTestPositions")
    public List<PositionResponseDto> getGenericPositions(){
            return positionService.getGenericPositions();
    }
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/addPositionToDepartment/{departmentId}")
    public void  addPositionToDepartment(@PathVariable UUID departmentId, @Valid @RequestBody PositionRequestDto requestDto){
            positionService.addPositionToDepartment(departmentId,requestDto);
    }


}
