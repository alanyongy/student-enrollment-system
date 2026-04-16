package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Enrollment;

import java.util.List;

public interface EnrollmentDAO {
        void enrollStudentInSection(Long studentId, Long sectionId);
        void dropStudentFromSection(Long studentId, Long sectionId);

        boolean existsByStudentAndSection(Long studentId, Long sectionId);

        List<Enrollment> findByStudentAndSemester(Long studentId, Long semesterId);

        long countBySection(Long sectionId);
        List<Enrollment> findAllEnrollments();
}
