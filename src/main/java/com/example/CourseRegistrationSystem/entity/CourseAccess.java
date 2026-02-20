package com.example.CourseRegistrationSystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "course_access")
@Getter
@Setter
public class CourseAccess {
    private Boolean required;
    
    @ManyToOne
    @MapsId("programId")
    private Program program;

    @ManyToOne
    @MapsId("courseId")
    private Course course;
}
