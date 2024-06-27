package com.example.test.module;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class TesterResult extends BaseEntity {
    @OneToOne
    private TesterHistory testerHistory;
    private Integer totalTest;
    private Integer correctTest;
    private Integer percentage;
    @ManyToOne
    private Result result;
}
