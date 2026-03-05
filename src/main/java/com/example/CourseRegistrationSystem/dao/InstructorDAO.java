package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Course;
import com.example.CourseRegistrationSystem.entity.Instructor;

import java.util.List;
import java.util.Optional;

public interface InstructorDAO {
    List<Instructor> findAll();

    Optional<Instructor> findById(Long id);

    Instructor save(Instructor instructor);

    void delete(Instructor instructor);
}
