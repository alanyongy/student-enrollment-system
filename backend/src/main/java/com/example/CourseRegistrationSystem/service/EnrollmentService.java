package com.example.CourseRegistrationSystem.service;

import java.util.List;

import com.example.CourseRegistrationSystem.entity.Course;
import com.example.CourseRegistrationSystem.entity.Enrollment;

public interface EnrollmentService {
        void enrollStudentInCourse(Long studentId, Long courseId);
        void dropStudentFromCourse(Long studentId, Long courseId);
        void dropEnrollment(Long enrollmentId);
        Enrollment updateEnrollment(Long enrollmentId, Enrollment enrollment);
        List<Enrollment> getAllEnrollments(int page, int size, String sortBy, String direction);

}
