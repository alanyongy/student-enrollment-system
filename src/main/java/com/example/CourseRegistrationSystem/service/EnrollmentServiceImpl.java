package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.dao.EnrollmentDAO;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentServiceImpl implements EnrollmentService{

    private final EnrollmentDAO enrollmentDAO;

        public EnrollmentServiceImpl(EnrollmentDAO enrollmentDAO) {
            this.enrollmentDAO = enrollmentDAO;
        }
    @Override
    public void enrollStudentInCourse(Long studentId, Long courseId) {
        enrollmentDAO.enrollStudentInSection(studentId, courseId);
    }

    @Override
    public void dropStudentFromCourse(Long studentId, Long courseId) {
        enrollmentDAO.dropStudentFromSection(studentId, courseId);
    }
}
