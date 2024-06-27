package com.example.test.service;

import com.example.test.dto.admin.UpdatePasswordDto;
import com.example.test.dto.admin.UpdateUserRequestDto;
import com.example.test.dto.admin.UserRequestDto;
import com.example.test.dto.user.UserResponseDto;
import com.example.test.enums.UserType;
import com.example.test.exceptions.DataAlreadyExists;
import com.example.test.exceptions.DataNotFoundException;
import com.example.test.exceptions.InputException;
import com.example.test.module.User;
import com.example.test.repository.UserRepository;
import com.example.test.util.Variables;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    public void addNewAdmin(UserRequestDto adminRequestDto){
        Boolean existEmail = userRepository.existsByEmail(adminRequestDto.getEmail());
        if(existEmail) throw  new DataAlreadyExists(Variables.ALL_READY_EXISTS.formatted("Bunday email"));
        if (!adminRequestDto. getPassword().equals(adminRequestDto.getConfirmPassword())) {
            throw new InputException(Variables.INCORRECT_PASSWORD);
        }
        User newAdmin = mapToEntity(adminRequestDto);
        userRepository.save(newAdmin);
    }
    public String updatePassword(@NonNull UUID userId, UpdatePasswordDto passwordDto){
        if(!passwordDto.getNewPassword().equals(passwordDto.getConfirmPassword())) throw new InputException(Variables.INCORRECT_PASSWORD);
        User updatedUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(Variables.NOT_FOUND.formatted("Foydalanuvchi")));
        if(!passwordEncoder.matches(passwordDto.getOldPassword(),updatedUser.getPassword())){
            throw new InputException("Parol no'to'g'ri.Iltimos tekshirib to'g'ri parol kirgazing");
        }

        updatedUser.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        updatedUser.setUpdatedDate(LocalDateTime.now());
        userRepository.save(updatedUser);
        return passwordDto.getNewPassword();

    }

    public void deleteById(UUID id) {
        if (userRepository.existsById(id)) userRepository.deleteById(id) ;

    throw new DataNotFoundException(Variables.NOT_FOUND.formatted("Foydalanuvchi"));
    }

    public List<UserResponseDto> getAllAdmins() {
      return userRepository.findAllByRole(UserType.ADMIN)
              .stream()
              .map(this::mapToResponse)
              .collect(Collectors.toList());
    }

    public UserResponseDto getById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(Variables.NOT_FOUND.formatted("Foydalanuvchi")));
        return mapToResponse(user);
    }

    public UserResponseDto updateUserAccount(UUID userId, UpdateUserRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) throw new DataAlreadyExists(Variables.ALL_READY_EXISTS.formatted("Email"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(Variables.NOT_FOUND.formatted("Foydalanuvchi")));

        user.setUpdatedDate(LocalDateTime.now());
        user.setEmail(requestDto.getEmail());
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        userRepository.save(user);
       return mapToResponse(user);
    }
    private User mapToEntity(UserRequestDto requestDto){
        User mapUser = modelMapper.map(requestDto, User.class);
        mapUser.setRole(UserType.ADMIN);
        return mapUser;
    }
    private  UserResponseDto mapToResponse(User user){
        return modelMapper.map(user, UserResponseDto.class);
    }
}
