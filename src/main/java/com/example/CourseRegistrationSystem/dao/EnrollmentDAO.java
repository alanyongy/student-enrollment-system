package com.example.CourseRegistrationSystem.dao;

public interface EnrollmentDAO {
        void enrollStudentInSection(Long studentId, Long sectionId);
        void dropStudentFromSection(Long studentId, Long sectionId);
}
