package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.entity.Semester;
import java.util.List;

public interface SemesterService {
    List<Semester> getAllSemesters(int page, int size, String sortBy, String direction);
    Semester getSemesterById(Long id);
    Semester createSemester(Semester semester);
    Semester updateSemester(Long id, Semester semester);
    void deleteSemester(Long id);
}

