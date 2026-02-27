package com.example.CourseRegistrationSystem.repository;

import com.example.CourseRegistrationSystem.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface SemesterRepository extends JpaRepository<Semester, Long> {
    
}
