package com.example.CourseRegistrationSystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
@Getter
@Setter
public class Student extends Person {

    private Integer enrollmentYear;
    private String academicStatus;
    private Integer totalCreditsEarned;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "major_department_id")
    private Department majorDepartment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "minor_department_id")
    private Department minorDepartment;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<Enrollment> enrollments = new ArrayList<>();
}

