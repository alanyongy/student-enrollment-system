package com.example.CourseRegistrationSystem.service;

public interface EnrollmentService {
        void enrollStudentInCourse(Long studentId, Long courseId);
        void dropStudentFromCourse(Long studentId, Long courseId);
}
