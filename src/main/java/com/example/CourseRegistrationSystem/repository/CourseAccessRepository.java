package com.example.CourseRegistrationSystem.repository;

import com.example.CourseRegistrationSystem.entity.CourseAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CourseAccessRepository extends JpaRepository<CourseAccess, Long> {
    
}