package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.dao.CourseDAO;
import com.example.CourseRegistrationSystem.dao.CoursePrerequisiteDAO;
import com.example.CourseRegistrationSystem.entity.Course;
import com.example.CourseRegistrationSystem.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CoursePrerequisiteServiceImpl implements CoursePrerequisiteService {

    private final CourseDAO courseDAO;
    private final CoursePrerequisiteDAO coursePrerequisiteDAO;

    @Autowired
    public CoursePrerequisiteServiceImpl(CourseDAO courseDAO, CoursePrerequisiteDAO coursePrerequisiteDAO) {
        this.courseDAO = courseDAO;
        this.coursePrerequisiteDAO = coursePrerequisiteDAO;
    }

    @Transactional
    @Override
    public void addPrerequisite(Long courseId, Long prerequisiteCourseId) {
        Course course = courseDAO.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + courseId));
        Course prerequisiteCourse = courseDAO.findById(prerequisiteCourseId)
                .orElseThrow(() -> new ResourceNotFoundException("Prerequisite course not found with id " + prerequisiteCourseId));
        coursePrerequisiteDAO.addPrerequisite(course, prerequisiteCourse);
    }

    @Transactional
    @Override
    public void removePrerequisite(Long courseId, Long prerequisiteCourseId) {
        Course course = courseDAO.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + courseId));
        Course prerequisiteCourse = courseDAO.findById(prerequisiteCourseId)
                .orElseThrow(() -> new ResourceNotFoundException("Prerequisite course not found with id " + prerequisiteCourseId));
        coursePrerequisiteDAO.removePrerequisite(course, prerequisiteCourse);
    }
}

