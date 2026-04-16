package com.example.CourseRegistrationSystem.service;

import java.util.List;

import com.example.CourseRegistrationSystem.entity.Enrollment;

public interface EnrollmentService {
        void enrollStudentInCourse(Long studentId, Long courseId);
        void dropStudentFromCourse(Long studentId, Long courseId);
        List<Enrollment> getAllEnrollments();
}
