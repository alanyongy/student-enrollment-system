package com.example.CourseRegistrationSystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "course_access",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"course_id", "program_id"}
    )
)
@Getter
@Setter
public class CourseAccess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Boolean required;
    
    @ManyToOne
    @MapsId("program_id")
    private Program program;

    @ManyToOne
    @MapsId("course_id")
    private Course course;
}
