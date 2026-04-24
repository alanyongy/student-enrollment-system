package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.dao.SectionDAO;
import com.example.CourseRegistrationSystem.dao.SemesterDAO;
import com.example.CourseRegistrationSystem.entity.Section;
import com.example.CourseRegistrationSystem.entity.Semester;
import com.example.CourseRegistrationSystem.exception.ResourceNotFoundException;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SemesterServiceImpl implements SemesterService {
    private final SemesterDAO semesterDAO;
    private final SectionDAO sectionDAO;

    @Autowired
    public SemesterServiceImpl(SemesterDAO semesterDAO, SectionDAO sectionDAO) {
        this.semesterDAO = semesterDAO;
        this.sectionDAO = sectionDAO;
    }


    @Override
    public List<Semester> getAllSemesters(int page, int size, String sortBy, String direction) {
        return semesterDAO.findAll(page, size, sortBy, direction);
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

    @Transactional
    public void deleteSemester(Long semesterId) {
        semesterDAO.delete(semesterId);
    }

    @Override
    public List<Semester> getSemestersForStudent(Long studentId) {
        return semesterDAO.findByStudentId(studentId);

    }
}

