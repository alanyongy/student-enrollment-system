package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.dao.SemesterDAO;
import com.example.CourseRegistrationSystem.entity.Semester;
import com.example.CourseRegistrationSystem.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SemesterServiceImpl implements SemesterService {

    @Autowired
    private SemesterDAO semesterDAO;

    @Override
    public List<Semester> getAllSemesters() {
        return semesterDAO.findAll();
    }

    @Override
    public Semester getSemesterById(Long id) {
        Semester semester = semesterDAO.findById(id);
        if (semester == null) {
            throw new ResourceNotFoundException("Semester not found with id: " + id);
        }
        return semester;
    }

    @Override
    public Semester createSemester(Semester semester) {
        return semesterDAO.save(semester);
    }

    @Override
    public Semester updateSemester(Long id, Semester semester) {
        Semester existing = getSemesterById(id);
        existing.setTermName(semester.getTermName());
        existing.setStartDate(semester.getStartDate());
        existing.setEndDate(semester.getEndDate());
        return semesterDAO.update(existing);
    }

    @Override
    public void deleteSemester(Long id) {
        semesterDAO.delete(id);
    }
}

