package com.example.CourseRegistrationSystem.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "completed_courses")
@Getter
@Setter
public class CompletedCourse {
    @ManyToOne
    @MapsId("studentId")
    private Student student;

    @ManyToOne
    @MapsId("courseId")
    private Course course;
}