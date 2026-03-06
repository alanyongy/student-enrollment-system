package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDAO {

    List<Student> findAll();

    Optional<Student> findById(Long id);

    Student save(Student student);

    void delete(Student student);
}
