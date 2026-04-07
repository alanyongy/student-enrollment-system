package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.dao.CompletedCourseDAO;
import com.example.CourseRegistrationSystem.dao.CoursePrerequisiteDAO;
import com.example.CourseRegistrationSystem.dao.EnrollmentDAO;
import com.example.CourseRegistrationSystem.dao.SectionDAO;
import com.example.CourseRegistrationSystem.dto.EnrollmentResponseDTO;
import com.example.CourseRegistrationSystem.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class StudentEnrollmentServiceImpl implements StudentEnrollmentService {

    private final EnrollmentDAO enrollmentDAO;
    private final SectionDAO sectionDAO;
    private final CompletedCourseDAO completedCourseDAO;
    private final CoursePrerequisiteDAO coursePrerequisiteDAO;

    @PersistenceContext
    private EntityManager entityManager;

    public StudentEnrollmentServiceImpl(EnrollmentDAO enrollmentDAO,
                                        SectionDAO sectionDAO,
                                        CompletedCourseDAO completedCourseDAO,
                                        CoursePrerequisiteDAO coursePrerequisiteDAO) {
        this.enrollmentDAO = enrollmentDAO;
        this.sectionDAO = sectionDAO;
        this.completedCourseDAO = completedCourseDAO;
        this.coursePrerequisiteDAO = coursePrerequisiteDAO;
    }

    @Override
    @Transactional
    public EnrollmentResult enrollInSection(Long studentId, Long sectionId) {
        EnrollmentResult result = new EnrollmentResult();
        List<String> errors = new ArrayList<>();

        Student student = entityManager.find(Student.class, studentId);
        if (student == null) {
            result.setErrorType(EnrollmentResult.ErrorType.NOT_FOUND);
            errors.add("Student not found");
            result.setErrors(errors);
            return result;
        }

        Section section = sectionDAO.findById(sectionId);
        if (section == null) {
            result.setErrorType(EnrollmentResult.ErrorType.NOT_FOUND);
            errors.add("Invalid section");
            result.setErrors(errors);
            return result;
        }

        // TODO: Implement detailed checks: already enrolled, capacity, schedule conflict, max load

        // Prerequisite check (simple): all prerequisites must be completed
        Course course = section.getCourse();
        List<CoursePrerequisite> prerequisites = coursePrerequisiteDAO.getPrerequisites(course);
        if (!prerequisites.isEmpty()) {
            List<CompletedCourse> completedCourses = completedCourseDAO.findByStudentId(studentId);
            Set<Course> completed = new HashSet<>();
            for (CompletedCourse cc : completedCourses) {
                completed.add(cc.getCourse());
            }
            for (CoursePrerequisite prereq : prerequisites) {
                if (!completed.contains(prereq.getPrerequisiteCourse())) {
                    errors.add("Missing prerequisite: " + prereq.getPrerequisiteCourse().getCourseNumber());
                }
            }
        }

        if (!errors.isEmpty()) {
            result.setErrorType(EnrollmentResult.ErrorType.UNPROCESSABLE);
            result.setErrors(errors);
            return result;
        }

        // Perform enrollment
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setSection(section);
        entityManager.persist(enrollment);

        EnrollmentResponseDTO dto = mapToDto(enrollment);

        result.setSuccess(true);
        result.setEnrollment(dto);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnrollmentResponseDTO> getEnrollmentsForStudent(Long studentId) {
        // Assuming semester filter will be added later if needed; for now, fetch all
        // active enrollments for the student across semesters
        List<Enrollment> enrollments = entityManager.createQuery(
                "SELECT e FROM Enrollment e WHERE e.student.personId = :studentId",
                Enrollment.class)
                .setParameter("studentId", studentId)
                .getResultList();

        List<EnrollmentResponseDTO> dtos = new ArrayList<>();
        for (Enrollment e : enrollments) {
            dtos.add(mapToDto(e));
        }
        return dtos;
    }

    @Override
    @Transactional
    public boolean dropEnrollment(Long studentId, Long sectionId) {
        // Try to find enrollment by student and section
        String jpql = "SELECT e FROM Enrollment e WHERE e.student.personId = :studentId AND e.section.sectionId = :sectionId";
        List<Enrollment> enrollments = entityManager.createQuery(jpql, Enrollment.class)
                .setParameter("studentId", studentId)
                .setParameter("sectionId", sectionId)
                .getResultList();

        if (enrollments.isEmpty()) {
            return false;
        }

        // Assuming at most one active enrollment per (student, section)
        Enrollment enrollment = enrollments.get(0);
        entityManager.remove(enrollment);
        return true;
    }

    private EnrollmentResponseDTO mapToDto(Enrollment enrollment) {
        EnrollmentResponseDTO dto = new EnrollmentResponseDTO();
        Section section = enrollment.getSection();
        dto.setEnrollmentId(enrollment.getEnrollmentId());
        dto.setSectionId(section.getSectionId());
        dto.setCourseName(section.getCourse().getDescription());
        dto.setSemester(section.getSemester().getTermName());
        return dto;
    }
}
