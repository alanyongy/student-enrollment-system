package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.dao.CompletedCourseDAO;
import com.example.CourseRegistrationSystem.dao.StudentDAO;
import com.example.CourseRegistrationSystem.dao.CourseDAO;
import com.example.CourseRegistrationSystem.entity.CompletedCourse;
import com.example.CourseRegistrationSystem.entity.Student;
import com.example.CourseRegistrationSystem.entity.Course;
import com.example.CourseRegistrationSystem.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompletedCourseServiceImpl implements CompletedCourseService {
    private final CompletedCourseDAO completedCourseDAO;
    private final StudentDAO studentDAO;
    private final CourseDAO courseDAO;

    public CompletedCourseServiceImpl(CompletedCourseDAO completedCourseDAO, StudentDAO studentDAO, CourseDAO courseDAO) {
        this.completedCourseDAO = completedCourseDAO;
        this.studentDAO = studentDAO;
        this.courseDAO = courseDAO;
    }

    @Transactional
    @Override
    public void markCourseCompleted(Long studentId, Long courseId) {
        Student student = studentDAO.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + studentId));
        Course course = courseDAO.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + courseId));
        if (completedCourseDAO.findByStudentAndCourse(studentId, courseId).isPresent()) {
            throw new IllegalStateException("Course already marked as completed for student.");
        }
        CompletedCourse completedCourse = new CompletedCourse();
        completedCourse.setStudent(student);
        completedCourse.setCourse(course);
        completedCourseDAO.save(completedCourse);
    }

    @Transactional
    @Override
    public void removeCompletedCourse(Long studentId, Long courseId) {
        if (!completedCourseDAO.findByStudentAndCourse(studentId, courseId).isPresent()) {
            throw new ResourceNotFoundException("Completed course not found for student.");
        }
        completedCourseDAO.deleteByStudentAndCourse(studentId, courseId);
    }
}

