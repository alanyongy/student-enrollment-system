package com.example.CourseRegistrationSystem.service;

public interface CompletedCourseService {
    void markCourseCompleted(Long studentId, Long courseId);
    void removeCompletedCourse(Long studentId, Long courseId);
}

