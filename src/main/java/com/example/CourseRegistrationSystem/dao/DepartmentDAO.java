package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Department;
import java.util.List;
import java.util.Optional;

public interface DepartmentDAO {
    List<Department> findAll();
    Optional<Department> findById(Long id);
    Department save(Department department);
    void delete(Department department);
}

