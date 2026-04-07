package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.entity.CompletedCourse;
import com.example.CourseRegistrationSystem.entity.Course;

import java.util.Collection;
import java.util.List;

public interface CompletedCourseService {
    void markCourseCompleted(Long studentId, Long courseId);
    void removeCompletedCourse(Long studentId, Long courseId);

    List<CompletedCourse> getCompletedCoursesByStudentId(Long personId);
}

