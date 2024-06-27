package com.example.test.module;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OptionalTest extends BaseEntity{
    @OneToOne( fetch = FetchType.EAGER)
    private Position position;
    @ManyToOne(fetch = FetchType.LAZY)
    private TestSettings testSetting;
    private int count;
}
