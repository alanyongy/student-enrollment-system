package com.example.CourseRegistrationSystem.repository;

import com.example.CourseRegistrationSystem.entity.Admission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AdmissionRepository extends JpaRepository<Admission, Long> {
    
}