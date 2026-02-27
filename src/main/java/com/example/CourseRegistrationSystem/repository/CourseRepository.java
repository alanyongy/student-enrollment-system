package com.example.CourseRegistrationSystem.repository;

import com.example.CourseRegistrationSystem.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    
}
