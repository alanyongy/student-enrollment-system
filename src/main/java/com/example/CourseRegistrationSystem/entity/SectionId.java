package com.example.CourseRegistrationSystem.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class SectionId implements Serializable {

    private Long courseId;
    private Integer sectionNumber;
}
