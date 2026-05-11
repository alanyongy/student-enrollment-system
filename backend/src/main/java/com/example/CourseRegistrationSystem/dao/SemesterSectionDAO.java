package com.example.CourseRegistrationSystem.dao;

public interface SemesterSectionDAO {
    void addSectionToSemester(long semesterId, long sectionId);
    void removeSectionFromSemester(long semesterId, long sectionId);
    boolean isSectionInSemester(long semesterId, long sectionId);
}
