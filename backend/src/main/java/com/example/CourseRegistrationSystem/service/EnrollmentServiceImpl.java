package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.dao.EnrollmentDAO;
import com.example.CourseRegistrationSystem.dao.SectionDAO;
import com.example.CourseRegistrationSystem.dao.StudentDAO;
import com.example.CourseRegistrationSystem.entity.Course;
import com.example.CourseRegistrationSystem.entity.Enrollment;
import com.example.CourseRegistrationSystem.entity.Section;
import com.example.CourseRegistrationSystem.entity.Student;
import com.example.CourseRegistrationSystem.exception.ResourceNotFoundException;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class EnrollmentServiceImpl implements EnrollmentService{

    private final EnrollmentDAO enrollmentDAO;
    private final SectionDAO sectionDAO;
    private final StudentDAO studentDAO;

    public EnrollmentServiceImpl(EnrollmentDAO enrollmentDAO, SectionDAO sectionDAO, StudentDAO studentDAO) {
        this.enrollmentDAO = enrollmentDAO;
        this.sectionDAO = sectionDAO;
        this.studentDAO = studentDAO;
    }
    
    @Override
    public void enrollStudentInCourse(Long studentId, Long courseId) {
        enrollmentDAO.enrollStudentInSection(studentId, courseId);
    }

    @Override
    public void dropStudentFromCourse(Long studentId, Long courseId) {
        enrollmentDAO.dropStudentFromSection(studentId, courseId);
    }

    @Override
    public void dropEnrollment(Long enrollmentId) {
        enrollmentDAO.dropEnrollment(enrollmentId);
    }

    @Override
    @Transactional
    public Enrollment updateEnrollment(Long enrollmentId, Enrollment req) {

        Enrollment existing = enrollmentDAO.findById(enrollmentId);

        // Resolve Student
        if (req.getStudent() == null || req.getStudent().getPersonId() == null) {
            throw new IllegalArgumentException("Student is required");
        }

        Student student = studentDAO.findById(req.getStudent().getPersonId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found with id " +
                                req.getStudent().getPersonId()));

        // Resolve Section
        if (req.getSection() == null || req.getSection().getSectionId() == null) {
            throw new IllegalArgumentException("Section is required");
        }

        Section section = sectionDAO.findById(req.getSection().getSectionId());

        // Apply updates
        existing.setStudent(student);
        existing.setSection(section);
        existing.setStatus(req.getStatus());

        return enrollmentDAO.save(existing);
    }


    @Override
    public List<Enrollment> getAllEnrollments(int page, int size, String sortBy, String direction) {

        List<Enrollment> enrollments =
                enrollmentDAO.findAllEnrollments(page, size, sortBy, direction);

        for (Enrollment e : enrollments) {

            Section sec = e.getSection();
            if (sec != null) {
                sec.getSectionId();

                Course course = sec.getCourse();
                if (course != null) {
                    course.getCourseId();
                    course.getCourseNumber();
                    course.getDescription();
                }
            }

            if (e.getStudent() != null) {
                e.getStudent().getFirstName();
                e.getStudent().getLastName();
            }
        }

        return enrollments;
    }
}
