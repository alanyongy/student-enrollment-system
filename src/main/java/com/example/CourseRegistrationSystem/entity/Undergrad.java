package com.example.CourseRegistrationSystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "undergrads")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Undergrad extends Student {

    @Column(name = "year_of_study")
    private Integer yearOfStudy;
}

