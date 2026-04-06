package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Course;
import com.example.CourseRegistrationSystem.entity.Student;

import java.util.List;
import java.util.Optional;

public interface CourseDAO {

    List<Course> findAll(int page, int size, String sortBy, String direction);

    Optional<Course> findById(Long id);

    Course save(Course course);

    void delete(Course course);

    Course assignDepartment(Long courseId, Long deptId);

    Course removeDepartment(Long courseId);
}
