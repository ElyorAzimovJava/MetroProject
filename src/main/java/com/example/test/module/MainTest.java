package com.example.test.module;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class MainTest extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String name;
    @OneToMany(mappedBy = "mainTest",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Test> tests;
    private String positionName;
}
