package com.example.CourseRegistrationSystem.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "admissions",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"student_id", "program_id"}
    )
)
@Getter
@Setter
public class Admission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @MapsId("student_id")
    private Student student;

    @ManyToOne
    @MapsId("program_id")
    private Program program;
}