package com.example.CourseRegistrationSystem.repository;

import com.example.CourseRegistrationSystem.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface SectionRepository extends JpaRepository<Section, Long> {
    
}