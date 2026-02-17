package com.example.CourseRegistrationSystem.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class EnrollmentId implements Serializable {

    private Long studentId;
    private Long courseId;
    private Integer sectionNumber;
}

