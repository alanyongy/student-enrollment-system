package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.entity.Semester;
import java.util.List;

public interface SemesterService {
    List<Semester> getAllSemesters();
    Semester getSemesterById(Long id);
    Semester createSemester(Semester semester);
    Semester updateSemester(Long id, Semester semester);
    void deleteSemester(Long id);
}

