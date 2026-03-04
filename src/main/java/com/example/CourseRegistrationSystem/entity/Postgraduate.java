package com.example.CourseRegistrationSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "postgrads")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Postgraduate extends Student {

}

