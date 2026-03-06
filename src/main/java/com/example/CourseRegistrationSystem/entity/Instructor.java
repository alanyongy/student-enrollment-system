package com.example.CourseRegistrationSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "instructors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Instructor extends Person {

    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

    @Column(name = "expertise_topics")
    private String expertiseTopics;
}

