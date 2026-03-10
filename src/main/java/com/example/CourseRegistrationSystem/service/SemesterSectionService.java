package com.example.CourseRegistrationSystem.service;

public interface SemesterSectionService {
        void addSemesterSection(Long semesterId, Long sectionId);
        void removeSemesterSection(Long semesterId, Long sectionId);
}
