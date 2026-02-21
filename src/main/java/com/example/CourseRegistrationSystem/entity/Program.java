package com.example.CourseRegistrationSystem.entity;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "programs",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"department_id", "program_id"}
    )
)
@Getter
@Setter
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String programName;
    private String description;

    
    @Column(name = "program_id", nullable = false)
    private Integer programId;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "program")
    private Set<Admission> admissions = new HashSet<>();

    @OneToMany(mappedBy = "program")
    private Set<CourseAccess> courseAccesses = new HashSet<>();
}
