package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.entity.Course;
import com.example.CourseRegistrationSystem.entity.Instructor;

import java.util.List;

public interface CourseService {
    List<Course> getCourses();

    Course getCourse(Long id);

    Course createCourse(Course course);

    Course updateCourse(Long id, Course course);

    void deleteCourse(Long id);
}
