package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.entity.Course;
import com.example.CourseRegistrationSystem.entity.CoursePrerequisite;

import java.util.List;

public interface CoursePrerequisiteService {
    void addPrerequisite(Long courseId, Long prerequisiteCourseId);
    void removePrerequisite(Long courseId, Long prerequisiteCourseId);

    List<CoursePrerequisite> getPrerequisitesForCourse(Long courseId);
}

