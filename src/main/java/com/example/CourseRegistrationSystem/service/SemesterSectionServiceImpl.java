package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.dao.SectionDAO;
import com.example.CourseRegistrationSystem.dao.SemesterSectionDAO;
import com.example.CourseRegistrationSystem.entity.Section;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SemesterSectionServiceImpl implements  SemesterSectionService{

    private final SemesterSectionDAO semesterSectionDAO;
    private final SectionDAO sectionDAO;

    public SemesterSectionServiceImpl(SemesterSectionDAO semesterSectionDAO, SectionDAO sectionDAO) {
        this.semesterSectionDAO = semesterSectionDAO;
        this.sectionDAO = sectionDAO;
    }

    @Override
    public void addSemesterSection(Long semesterId, Long sectionId) {
        semesterSectionDAO.addSectionToSemester(semesterId, sectionId);
    }

    @Override
    public void removeSemesterSection(Long semesterId, Long sectionId) {
        semesterSectionDAO.removeSectionFromSemester(semesterId, sectionId);
    }

    @Override
    public List<Long> getSectionsForSemester(Long semesterId) {

        List<Section> sections = sectionDAO.findBySemesterId(semesterId);
        return sections.stream().map(Section::getSectionId).toList();

    }
}
