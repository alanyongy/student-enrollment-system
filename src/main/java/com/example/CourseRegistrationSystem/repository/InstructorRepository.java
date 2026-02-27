package com.example.CourseRegistrationSystem.repository;

import com.example.CourseRegistrationSystem.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    
}