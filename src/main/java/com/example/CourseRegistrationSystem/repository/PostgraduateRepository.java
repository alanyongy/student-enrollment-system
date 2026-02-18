package com.example.CourseRegistrationSystem.repository;

import com.example.CourseRegistrationSystem.entity.Postgraduate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface PostgraduateRepository extends JpaRepository<Postgraduate, Long> {
    
}