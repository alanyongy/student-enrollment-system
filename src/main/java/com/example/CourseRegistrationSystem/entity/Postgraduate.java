package com.example.CourseRegistrationSystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "postgraduates")
@Getter
@Setter
public class Postgraduate extends Student {

    private String dissertationTitle;
    private String researchTopic;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "supervisor_id")
    private Instructor supervisor;
}

