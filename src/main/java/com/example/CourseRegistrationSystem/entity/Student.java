package com.example.CourseRegistrationSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public abstract class Student extends Person {

    @NotNull(message = "Enrollment year cannot be null")
    @Positive(message = "Enrollment year must be a valid year")
    @Column(name = "enrollment_year")
    private Integer enrollmentYear;

    @NotBlank(message = "Academic status cannot be blank")
    @Column(name = "academic_status")
    private String academicStatus;

    @NotNull(message = "Credits earned cannot be null")
    @PositiveOrZero(message = "Credits earned must be zero or positive")
    @Column(name = "credits_earned")
    private Integer creditsEarned;

    @PositiveOrZero(message = "GPA must be zero or positive")
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
