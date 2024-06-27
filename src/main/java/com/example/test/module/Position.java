package com.example.test.module;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "name_department_unique_constraint",
                columnNames = {"name","department"}
        ),
})
public class Position extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department")
    private Department department;
    private Boolean isForTestOrRole;
    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isDeleted;
}
