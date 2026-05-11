package com.example.CourseRegistrationSystem.service;

import java.util.List;

public interface SemesterSectionService {
        void addSemesterSection(Long semesterId, Long sectionId);
        void removeSemesterSection(Long semesterId, Long sectionId);
        List<Long> getSectionsForSemester(Long semesterId);
}
