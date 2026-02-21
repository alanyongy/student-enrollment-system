package com.example.CourseRegistrationSystem.repository;

import com.example.CourseRegistrationSystem.entity.CompletedCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CompletedCourseRepository extends JpaRepository<CompletedCourse, Long> {
    
}