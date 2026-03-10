package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.CoursePrerequisite;
import com.example.CourseRegistrationSystem.entity.Course;

import java.util.List;

public interface CoursePrerequisiteDAO {
    CoursePrerequisite addPrerequisite(Course course, Course prerequisiteCourse);
    void removePrerequisite(Course course, Course prerequisiteCourse);
    List<CoursePrerequisite> getPrerequisites(Course course);
}

