package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> getCourses(int page, int size, String sortyBy, String direction);

    Course getCourse(Long id);

    Course createCourse(Course course);

    Course updateCourse(Long id, Course course);

    void deleteCourse(Long id);

    void assignDepartment(Long courseId, Long deptId);

    void removeDepartment(Long courseId);
}
