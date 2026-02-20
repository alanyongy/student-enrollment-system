package com.example.CourseRegistrationSystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "instructors")
@Getter
@Setter
public class Instructor extends Person {

    private Integer yearsOfExperience;
    private String expertiseTopics;

    @OneToMany(mappedBy = "instructor", fetch = FetchType.LAZY)
    private List<Section> sections = new ArrayList<>();
}

