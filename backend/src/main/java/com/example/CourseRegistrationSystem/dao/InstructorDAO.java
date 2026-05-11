package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Course;
import com.example.CourseRegistrationSystem.entity.Instructor;

import java.util.List;
import java.util.Optional;

public interface InstructorDAO {
    List<Instructor> findAll(int page, int size, String sortBy, String direction);

    Optional<Instructor> findById(Long id);

    Instructor save(Instructor instructor);

    void delete(Instructor instructor);
}
