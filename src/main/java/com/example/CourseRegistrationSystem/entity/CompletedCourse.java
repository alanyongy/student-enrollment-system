package com.example.CourseRegistrationSystem.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "completed_courses",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"student_id", "course_id"}
    )
)
@Getter
@Setter
public class CompletedCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @MapsId("student_id")
    private Student student;

    @ManyToOne
    @MapsId("course_id")
    private Course course;
}