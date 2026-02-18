package com.example.CourseRegistrationSystem.repository;

import com.example.CourseRegistrationSystem.entity.Undergrad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UndergraduateRepository extends JpaRepository<Undergrad, Long> {
    
}