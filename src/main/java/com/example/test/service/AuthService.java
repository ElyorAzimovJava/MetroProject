package com.example.test.service;

import com.example.test.dto.auth.ApiResponse;
import com.example.test.dto.auth.JwtResponseDto;
import com.example.test.dto.auth.LoginDto;
import com.example.test.dto.position.PositionResponseDto;
import com.example.test.dto.testter.TesterRequestDto;
import com.example.test.dto.testter.TesterResponse;
import com.example.test.dto.user.UserResponseDto;
import com.example.test.enums.UserType;
import com.example.test.exceptions.DataAlreadyExists;
import com.example.test.exceptions.DataNotFoundException;
import com.example.test.exceptions.InputException;
import com.example.test.exceptions.LoginException;
import com.example.test.jwt.JwtService;
import com.example.test.module.BaseEntity;
import com.example.test.module.Position;
import com.example.test.module.Tester;
import com.example.test.module.User;
import com.example.test.repository.PositionRepository;
import com.example.test.repository.TesterRepository;
import com.example.test.repository.UserRepository;
import com.example.test.util.Variables;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final TesterRepository testerRepository;
    private final PositionRepository positionRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public ApiResponse<?> login(@NonNull LoginDto loginDto){
        Optional<User> admin = userRepository.findUserByEmail(loginDto.getEmail());
        Optional<Tester> tester = testerRepository.findByIdNumber(loginDto.getEmail());
        if(admin.isEmpty() && tester.isEmpty()){
            throw new LoginException("Notogri email, idNumber yoki parol");
        }
        else if(admin.isPresent()){
            User user = admin.get();
            if(!passwordEncoder.matches(loginDto.getPassword(),user.getPassword()))
                throw new LoginException("Notogri email, idNumber yoki parol");
            UserResponseDto userResponseDto = new UserResponseDto(user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole(), user.getCreatedDate(), user.getUpdatedDate());
            return new ApiResponse<UserResponseDto> (userResponseDto,new JwtResponseDto(jwtService.generateToken(user)));
        }else {
            Tester tes = tester.get();
            if(!passwordEncoder.matches(loginDto.getPassword(),tes.getPassword()))
                throw new LoginException("Notogri email, idNumber yoki parol");
            TesterResponse testerResponse = mapToResponse(tes);
            return new ApiResponse<>(testerResponse, new JwtResponseDto(jwtService.generateToken(tes)));
        }
    }
    public TesterResponse registerTester(@NonNull TesterRequestDto requestDto){
        if(!requestDto.getPassword().equals(requestDto.getConfirmPassword())){
            throw new InputException(Variables.INCORRECT_PASSWORD);
        }
        Boolean existIdNumber = testerRepository.existsByIdNumberOrPassportSerial(requestDto.getIdNumber(),requestDto.getPassportSerial());
        if(existIdNumber){
            throw new DataAlreadyExists(Variables.ALL_READY_EXISTS.formatted("Bunday id raqamli yoki passport Seriali foydalanuvchi "));
        }
        Position position = positionRepository.findByIsDeletedFalseAndId(requestDto.getPositionId())
                .orElseThrow(() -> new DataNotFoundException(Variables.NOT_FOUND.formatted("Lavozim")));
        if (!position.getDepartment().getId().equals(requestDto.getDepartmentId())) {
            throw new InputException("Departmentda bunaqa lavozim mavjud emas");
        }
        Tester tester = mapToEntity(requestDto);
        tester.setRole(UserType.TESTER);
        tester.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        testerRepository.save(tester);
        return mapToResponse(tester);

    }
    private TesterResponse mapToResponse(Tester tester){
        TesterResponse response = modelMapper.map(tester, TesterResponse.class);
         response.setPosition(mapToPositionResponse(tester.getPosition()));
         return response;
    }
    private  Tester mapToEntity(TesterRequestDto requestDto){
        Tester tester = modelMapper.map(requestDto, Tester.class);
        Position position = positionRepository.findById(requestDto.getPositionId())
                .orElseThrow(() -> new DataNotFoundException(Variables.NOT_FOUND.formatted("Lavozim")));
        tester.setPosition(position);
        return tester;

    }
    private PositionResponseDto mapToPositionResponse(Position position){
        PositionResponseDto map = modelMapper.map(position, PositionResponseDto.class);
        if(position.getDepartment() != null){
            map.setDepartmentId(position.getDepartment().getId());
            map.setDepartmentName(position.getDepartment().getName());
        }
        return map;
    }

}
