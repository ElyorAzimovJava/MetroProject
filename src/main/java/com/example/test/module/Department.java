package com.example.test.module;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "name_isDeleted",
                columnNames = {"name","is_deleted"}
        )
})
public class Department extends BaseEntity{
    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy = "department",fetch = FetchType.EAGER)
    private List<Position> positions;
    private Boolean isDeleted;
}
