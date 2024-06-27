package com.example.test.controller;

import com.example.test.dto.admin.UpdatePasswordDto;
import com.example.test.dto.admin.UpdateUserRequestDto;
import com.example.test.dto.admin.UserRequestDto;
import com.example.test.dto.user.UserResponseDto;
import com.example.test.service.UserService;
import com.example.test.util.Variables;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/addAdmin")
    public void addAddAdmin(@Valid @RequestBody UserRequestDto adminRequestDto){
          userService.addNewAdmin(adminRequestDto);
    }
    @GetMapping("/allAdmins")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public List<UserResponseDto> getAllAdmins(){
        return userService.getAllAdmins();
    }
    @GetMapping("/getById/{id}")
    public UserResponseDto getById(@PathVariable UUID id){
        return  userService.getById(id);
    }
    @PatchMapping("/updatePassword/{userId}")
    public String  updatePassword(@PathVariable UUID userId,
                                  @Valid UpdatePasswordDto passwordDto){
        return userService.updatePassword(userId,passwordDto);
    }
    @DeleteMapping("/deleteById/{id}")
    public void deleteUserById(@PathVariable UUID id){
        userService.deleteById(id);
    }
    @PutMapping("/updateAccount/{userId}")
    public UserResponseDto  updateAccount(@PathVariable UUID userId, @Valid @RequestBody UpdateUserRequestDto userRequestDto){
        return userService.updateUserAccount(userId,userRequestDto);
    }

}
