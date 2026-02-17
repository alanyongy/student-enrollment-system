package com.example.CourseRegistrationSystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "graduates")
@Getter
@Setter
public class Graduate extends Student {

    private Boolean researchStudent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervisor_id")
    private Instructor supervisor;

    private String thesisTitle;
    private String researchTopic;
}

