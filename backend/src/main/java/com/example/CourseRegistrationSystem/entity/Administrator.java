package com.example.CourseRegistrationSystem.entity;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
public class Administrator extends Person {
    // extra admin-specific behavior (methods) only, no JPA mapping
}