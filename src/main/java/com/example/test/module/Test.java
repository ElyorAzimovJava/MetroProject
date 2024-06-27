package com.example.test.module;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Test  extends BaseEntity{
    @Column(nullable = false)
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
    private Integer code;
    @ManyToOne
    private MainTest mainTest;
}
