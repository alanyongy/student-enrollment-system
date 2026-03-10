package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.dao.SemesterSectionDAO;
import org.springframework.stereotype.Service;

@Service
public class SemesterSectionServiceImpl implements  SemesterSectionService{

    private final SemesterSectionDAO semesterSectionDAO;

    public SemesterSectionServiceImpl(SemesterSectionDAO semesterSectionDAO) {
        this.semesterSectionDAO = semesterSectionDAO;
    }

    @Override
    public void addSemesterSection(Long semesterId, Long sectionId) {
        semesterSectionDAO.addSectionToSemester(semesterId, sectionId);
    }

    @Override
    public void removeSemesterSection(Long semesterId, Long sectionId) {
        semesterSectionDAO.removeSectionFromSemester(semesterId, sectionId);
    }
}
