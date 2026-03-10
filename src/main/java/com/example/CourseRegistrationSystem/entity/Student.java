package com.example.CourseRegistrationSystem.entity;

import jakarta.persistence.*;
import lombok.*;

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

    public Integer getEnrollmentYear() {
        return enrollmentYear;
    }

    public void setEnrollmentYear(Integer enrollmentYear) {
        this.enrollmentYear = enrollmentYear;
    }

    public String getAcademicStatus() {
        return academicStatus;
    }

    public void setAcademicStatus(String academicStatus) {
        this.academicStatus = academicStatus;
    }

    public Integer getCreditsEarned() {
        return creditsEarned;
    }

    public void setCreditsEarned(Integer creditsEarned) {
        this.creditsEarned = creditsEarned;
    }

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }
}
