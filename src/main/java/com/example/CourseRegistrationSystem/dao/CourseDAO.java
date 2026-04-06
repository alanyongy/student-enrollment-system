package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseDAO {

    List<Course> findAll(int page, int size, String sortBy, String direction);

    Optional<Course> findById(Long id);

    List<Course> findBySemesterId(Long semesterId);  // <-- new method

    Course save(Course course);

    void delete(Course course);

    Course assignDepartment(Long courseId, Long deptId);

    Course removeDepartment(Long courseId);

    List<Course> findAllBySemester(Long semesterId);

    Optional<Course> findByIdAndSemester(Long courseId, Long semesterId);
}