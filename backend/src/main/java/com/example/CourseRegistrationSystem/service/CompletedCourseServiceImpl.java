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

import java.util.List;

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

    @Override
    public List<CompletedCourse> getCompletedCoursesByStudentId(Long personId) {
        Student student = studentDAO.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + personId));

        List<CompletedCourse> completedCourses = completedCourseDAO.findByStudentId(personId);
        if (completedCourses.isEmpty()) {
            throw new ResourceNotFoundException("No completed courses found for student with id " + personId);
        }
        return completedCourses;

    }

    @Override
    public List<CompletedCourse> getAll(int page, int size, String sortBy, String direction) {
        return completedCourseDAO.findAll(page, size, sortBy, direction);
    }

    @Transactional
    @Override
    public CompletedCourse create(CompletedCourse req) {

        Long studentId = req.getStudent().getPersonId();
        Long courseId = req.getCourse().getCourseId();

        CompletedCourse cc = new CompletedCourse();

        cc.setStudent(
            studentDAO.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"))
        );

        cc.setCourse(
            courseDAO.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"))
        );

        return completedCourseDAO.save(cc);
    }

    @Transactional
    @Override
    public CompletedCourse update(Long id, CompletedCourse req) {

        CompletedCourse existing = completedCourseDAO.findById(id);

        if (existing == null) {
            throw new RuntimeException("Completion not found");
        }

        Long studentId = req.getStudent().getPersonId();
        Long courseId = req.getCourse().getCourseId();

        existing.setStudent(
            studentDAO.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"))
        );

        existing.setCourse(
            courseDAO.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"))
        );

        return completedCourseDAO.save(existing);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        CompletedCourse cc = completedCourseDAO.findById(id);

        if (cc == null) {
            throw new RuntimeException("Completion not found");
        }

        completedCourseDAO.delete(cc);
    }
}

