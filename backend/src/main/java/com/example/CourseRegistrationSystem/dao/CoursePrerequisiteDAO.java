package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.CoursePrerequisite;
import com.example.CourseRegistrationSystem.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CoursePrerequisiteDAO {
    void addPrerequisite(Course course, Course prerequisiteCourse);
    void removePrerequisite(Course course, Course prerequisiteCourse);
    List<CoursePrerequisite> getPrerequisites(Course course);

    List<CoursePrerequisite> findAll(int page, int size, String sortBy, String direction);
    Optional<CoursePrerequisite> findById(Long id);
    CoursePrerequisite save(CoursePrerequisite cp);
    void delete(CoursePrerequisite cp);
}

