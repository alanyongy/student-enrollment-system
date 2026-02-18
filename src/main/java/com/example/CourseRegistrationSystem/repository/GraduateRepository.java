package com.example.CourseRegistrationSystem.repository;

import com.example.CourseRegistrationSystem.entity.Graduate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface GraduateRepository extends JpaRepository<Graduate, Long> {
    
}