package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.entity.Instructor;
import com.example.CourseRegistrationSystem.entity.Student;

import java.util.List;
import java.util.Optional;

public interface InstructorService {

    List<Instructor> findAll();

    Optional<Instructor> findById(Long id);

    Student save(Instructor instructor);

        void delete(Instructor instructor);
}
