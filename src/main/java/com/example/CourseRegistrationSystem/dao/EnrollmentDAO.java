package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Enrollment;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

import org.springframework.stereotype.Repository;

public interface EnrollmentDAO {
        void enrollStudentInSection(Long studentId, Long sectionId);
        void dropStudentFromSection(Long studentId, Long sectionId);
        void dropEnrollment(Long EnrollmentId);

        boolean existsByStudentAndSection(Long studentId, Long sectionId);

        List<Enrollment> findByStudentAndSemester(Long studentId, Long semesterId);

        long countBySection(Long sectionId);
        List<Enrollment> findAllEnrollments(int page, int size, String sortBy, String direction);

        Enrollment save(Enrollment enrollment);
        Enrollment findById(Long id);
}
