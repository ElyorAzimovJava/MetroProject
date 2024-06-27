package com.example.test.module;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TesterHistory extends BaseEntity{
    @ManyToOne
    private Tester tester;
    private LocalDateTime testStartDate;
    private LocalDateTime testEndDate;
    @OneToMany
    private List<UserTestCollection> testCollections;
}
