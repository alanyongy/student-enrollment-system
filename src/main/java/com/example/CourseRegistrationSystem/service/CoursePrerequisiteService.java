package com.example.CourseRegistrationSystem.service;

public interface CoursePrerequisiteService {
    void addPrerequisite(Long courseId, Long prerequisiteCourseId);
    void removePrerequisite(Long courseId, Long prerequisiteCourseId);
}

