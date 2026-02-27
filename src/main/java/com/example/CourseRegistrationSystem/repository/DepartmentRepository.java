package com.example.CourseRegistrationSystem.repository;

import com.example.CourseRegistrationSystem.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface DepartmentRepository extends JpaRepository<Department, Long> {
    
}
