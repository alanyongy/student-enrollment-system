package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.CompletedCourse;

import java.util.Optional;
import java.util.List;

public interface CompletedCourseDAO {
    CompletedCourse save(CompletedCourse completedCourse);
    void deleteByStudentAndCourse(Long studentId, Long courseId);
    Optional<CompletedCourse> findByStudentAndCourse(Long studentId, Long courseId);
    List<CompletedCourse> findByStudentId(Long studentId);

    List<CompletedCourse> findAll(int page, int size, String sortBy, String direction);
    CompletedCourse findById(Long id);
    void delete(CompletedCourse completion);
}

