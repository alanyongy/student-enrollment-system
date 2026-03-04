package com.example.CourseRegistrationSystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public abstract class Student extends Person {

    @Column(name = "enrollment_year")
    private Integer enrollmentYear;

    @Column(name = "academic_status")
    private String academicStatus;

    @Column(name = "credits_earned")
    private Integer creditsEarned;

    @Transient
    private Double gpa;
}

