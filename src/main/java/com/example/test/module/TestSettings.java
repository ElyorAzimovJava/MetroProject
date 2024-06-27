package com.example.test.module;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "test_settings")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TestSettings extends BaseEntity {
    private int totalTestCount;
    private int mainPositionTestCount;
    private int timeOfTest;
    @OneToMany(mappedBy = "testSetting", fetch = FetchType.EAGER)
    private List<OptionalTest> optionalTests;

}
