package com.example.CourseRegistrationSystem.repository;

import com.example.CourseRegistrationSystem.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    
}
