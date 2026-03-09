package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Semester;
import java.util.List;

public interface SemesterDAO {
    List<Semester> findAll();
    Semester findById(Long id);
    Semester save(Semester semester);
    Semester update(Semester semester);
    void delete(Long id);
}

