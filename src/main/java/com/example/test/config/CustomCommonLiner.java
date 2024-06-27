package com.example.test.config;

import com.example.test.enums.ResultType;
import com.example.test.enums.UserType;
import com.example.test.module.*;
import com.example.test.repository.*;
import com.example.test.util.Variables;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class CustomCommonLiner implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final ResultRepository resultRepository;
    private final TestSettingRepository testSettingRepository;
    private final OptionalTestRepository optionalTestRepository;
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;
    @Override
    public void run(String... args) {
        if (ddlAuto.equals("create")) {
            String password = "ea1202";
            String passwordAdmin = "elyor1202";
            String encode = passwordEncoder.encode(password);
            String adminEncodedPassword = passwordEncoder.encode(passwordAdmin);
            userRepository.save(new User("Elyor", "Azimov", "elyorazimov098@gmail.com", encode, UserType.SUPER_ADMIN));
            userRepository.save(new User("Admin", "Adminovich", "admin@gmail.com", adminEncodedPassword, UserType.ADMIN));

            Department department1 = Department.builder()
                    .name(Variables.DEPARTMENT_ONE)
                    .isDeleted(false)
                    .build();
            Department department2 = Department.builder()
                    .name(Variables.DEPARTMENT_TWO)
                    .isDeleted(false)
                    .build();
            Department department3 = Department.builder()
                    .name(Variables.DEPARTMENT_THREE)
                    .isDeleted(false)
                    .build();
            departmentRepository.save(department1);
            departmentRepository.save(department2);
            departmentRepository.save(department3);
            Position positionOne = new Position(Variables.POSITION_ONE_OF_DEPARTMENT_ONE, department1, true,false);
            Position positionTwo = new Position(Variables.POSITION_TWO_OF_DEPARTMENT_ONE, department1, true,false);
            Position positionThree = new Position(Variables.POSITION_THREE_OF_DEPARTMENT_ONE, department1, true,false);
            Position positionFour = new Position(Variables.POSITION_ONE_OF_DEPARTMENT_TWO, department2, true,false);
            Position positionFive = new Position(Variables.POSITION_TWO_OF_DEPARTMENT_TWO, department2, true,false);
            Position positionSix = new Position(Variables.POSITION_THREE_OF_DEPARTMENT_TWO, department2, true,false);
            Position positionSeven = new Position(Variables.POSITION_ONE_OF_DEPARTMENT_THREE, department3, true,false);
            Position positionEight = new Position(Variables.POSITION_TWO_OF_DEPARTMENT_THREE, department3, true,false);
            Position positionNine = new Position(Variables.POSITION_THREE_OF_DEPARTMENT_THREE, department3, true,false);
            Position positionSocial = new Position(Variables.POSITION_FOR_SOCIALS,null,false,false);
            Position positionEconomic = new Position(Variables.POSITION_FOR_ECONOMIC,null,false,false);
            Position positionComputer = new Position(Variables.POSITION_FOR_ECONOMIC,null,false,false);
            positionRepository.save(positionOne);
            positionRepository.save(positionTwo);
            positionRepository.save(positionThree);
            positionRepository.save(positionFour);
            positionRepository.save(positionFive);
            positionRepository.save(positionSix);
            positionRepository.save(positionSeven);
            positionRepository.save(positionEight);
            positionRepository.save(positionNine);
            positionRepository.save(positionNine);
            positionRepository.save(positionComputer);
            Position social = positionRepository.save(positionSocial);
            Position economic = positionRepository.save(positionEconomic);
            Result resultOne = new Result(Variables.RESULT_ONE, ResultType.BAD, 0, 55);
            Result resultTwo = new Result(Variables.RESULT_TWO, ResultType.GOOD,56, 75);
            Result resultThree = new Result(Variables.RESULT_THREE,ResultType.EXCELLENT, 76, 95);
            Result resultFour = new Result(Variables.RESULT_FOUR,ResultType.PERFECT, 96, 100);
            resultRepository.save(resultOne);
            resultRepository.save(resultTwo);
            resultRepository.save(resultThree);
            resultRepository.save(resultFour);
            TestSettings testSettings = new TestSettings(50, 20, 30, null);
            testSettingRepository.save(testSettings);
            OptionalTest socialOptional = new OptionalTest(social, testSettings, 10);
            OptionalTest computerOptional = new OptionalTest(economic, testSettings, 10);
            OptionalTest economicPosition = new OptionalTest(positionComputer, testSettings, 10);
            optionalTestRepository.save(socialOptional);
            optionalTestRepository.save(computerOptional);
            optionalTestRepository.save(economicPosition);
        }
    }


}
