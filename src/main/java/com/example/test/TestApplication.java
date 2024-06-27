package com.example.test;

import com.example.test.module.OptionalTest;
import com.example.test.module.Position;
import com.example.test.module.TestSettings;
import com.example.test.repository.OptionalTestRepository;
import com.example.test.repository.PositionRepository;
import com.example.test.repository.TestSettingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
/*  @Bean
    public CommandLineRunner commandLineRunner(TestSettingRepository testSettingRepository, PositionRepository positionRepository, OptionalTestRepository optionalTestRepository){
      return args -> {
          TestSettings testSettings = new TestSettings(50, 50, 30, null);
          testSettingRepository.save(testSettings);
          List<Position> genericPositions = positionRepository.getGenericPositions();
          List<OptionalTest> list = genericPositions.stream()
                  .map(position -> new OptionalTest(position, testSettings,0))
                  .toList();
          optionalTestRepository.saveAll(list);
      };

  }*/

}
