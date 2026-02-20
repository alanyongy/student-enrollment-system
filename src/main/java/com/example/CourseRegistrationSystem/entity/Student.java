package com.example.CourseRegistrationSystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "students")
@Getter
@Setter
public class Student extends Person {

    private Integer enrollmentYear;
    private String academicStatus;
    private Integer totalCreditsEarned;

    @OneToMany(mappedBy = "student")
    private Set<CompletedCourse> completedCourses;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<Enrollment> enrollments = new ArrayList<>();
}

