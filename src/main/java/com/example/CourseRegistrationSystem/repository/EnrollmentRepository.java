package com.example.CourseRegistrationSystem.repository;

import com.example.CourseRegistrationSystem.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    
}