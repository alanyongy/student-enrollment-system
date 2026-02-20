package com.example.CourseRegistrationSystem.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "semesters")
public class Semester {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long termId;

    private String termName;
    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(mappedBy = "semester")
    private List<Section> sections = new ArrayList<>();
}
