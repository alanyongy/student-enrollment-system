package com.example.CourseRegistrationSystem.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "admissions")
@Getter
@Setter
public class Admission {
    @ManyToOne
    @MapsId("studentId")
    private Student student;

    @ManyToOne
    @MapsId("programId")
    private Program program;
}