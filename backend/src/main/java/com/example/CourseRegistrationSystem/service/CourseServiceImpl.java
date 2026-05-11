package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.dao.CourseDAO;
import com.example.CourseRegistrationSystem.dao.DepartmentDAO;
import com.example.CourseRegistrationSystem.entity.Course;
import com.example.CourseRegistrationSystem.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService{

    private final CourseDAO courseDAO;

    @Autowired
    private DepartmentDAO departmentDAO;

    @Autowired
    public CourseServiceImpl(CourseDAO courseDAO){
        this.courseDAO = courseDAO;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Course> getCourses(int page, int size, String sortBy, String direction) {
        return courseDAO.findAll(page, size, sortBy, direction);
    }

    @Transactional(readOnly = true)
    @Override
    public Course getCourse(Long id) {
        return courseDAO
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course not found with id " + id));
    }

    @Transactional
    @Override
    public Course createCourse(Course course) {
        course.setCourseId(null);
        return courseDAO.save(course);
    }

    @Transactional
    @Override
    public Course updateCourse(Long id, Course course) {
        Course existingCourse = courseDAO.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course not found with id " + id));
        existingCourse.setCourseId(course.getCourseId());
        if (course.getCourseNumber() != null)
            existingCourse.setCourseNumber(course.getCourseNumber());
        if (course.getDescription() != null)
            existingCourse.setDescription(course.getDescription());
        if (course.getCredits() != null)
            existingCourse.setCredits(course.getCredits());
        if (course.getDepartment() != null)
            existingCourse.setDepartment(course.getDepartment());

        return courseDAO.save(existingCourse);
    }

    @Transactional
    @Override
    public void deleteCourse(Long id) {

            Course course = courseDAO.
                    findById(id)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Course not found with id " + id));

            courseDAO.delete(course);
    }

    @Transactional
    @Override
    public void assignDepartment(Long courseId, Long deptId) {
        courseDAO.assignDepartment(courseId, deptId);
    }

    @Transactional
    @Override
    public void removeDepartment(Long courseId) {
        courseDAO.removeDepartment(courseId);
    }
}
