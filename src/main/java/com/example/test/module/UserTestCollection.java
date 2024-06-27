package com.example.test.module;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class UserTestCollection extends BaseEntity{
    @ManyToOne
    private TesterHistory  testerHistory;
    private UUID testId;
    private String title;
    @Column(nullable = false)
    private String variantFirst;
    @Column(nullable = false)
    private String variantSecond;
    @Column(nullable = false)
    private String variantThird;
    @Column(nullable = false)
    private String variantFourth;
    @Column(nullable = false)
    private String variantTrue;
    private Integer testNumber;
    private boolean isTrueAnswer;
    private boolean isLastTest;
    private boolean isFirstTest;
    private String selectVariant;
}
