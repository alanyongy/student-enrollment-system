package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDAO {

    List<Student> findAll(int page, int size, String sortBy, String direction);

    Optional<Student> findById(Long id);

    Student save(Student student);

    void delete(Student student);
}
