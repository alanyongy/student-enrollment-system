package com.example.CourseRegistrationSystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "undergraduates")
@Getter
@Setter
public class Undergrad extends Student {

    private Integer yearOfStudy;
}

