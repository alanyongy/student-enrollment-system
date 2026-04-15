package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Enrollment;
import com.example.CourseRegistrationSystem.entity.Section;
import com.example.CourseRegistrationSystem.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public class EnrollmentDAOImpl implements EnrollmentDAO{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void enrollStudentInSection(Long studentId, Long sectionId) {
        Enrollment enrollment = new Enrollment();
        Student student = entityManager.find(Student.class, studentId);
        if (student == null) {
            throw new IllegalArgumentException("Student with ID " + studentId + " not found");
        }

        enrollment.setStudent(student);
        Section section = entityManager.find(Section.class, sectionId);
        if (section == null) {
            throw new IllegalArgumentException("Section with ID " + sectionId + " not found");
        }
        enrollment.setSection(section);
        entityManager.persist(enrollment);

    }

    @Override
    public void dropStudentFromSection(Long studentId, Long sectionId) {
        String jpql = "SELECT e FROM Enrollment e WHERE e.student.id = :studentId AND e.section.id = :sectionId";
        Enrollment enrollment = entityManager.createQuery(jpql, Enrollment.class)
                .setParameter("studentId", studentId)
                .setParameter("sectionId", sectionId)
                .getSingleResult();
        if (enrollment != null) {
            entityManager.remove(enrollment);
        } else {
            throw new IllegalArgumentException("Enrollment not found for student ID " + studentId + " and section ID " + sectionId);
        }
    }

    @Override
    public boolean existsByStudentAndSection(Long studentId, Long sectionId) {
        String jpql = "SELECT COUNT(e) FROM Enrollment e WHERE e.student.personId = :studentId AND e.section.sectionId = :sectionId";
        Long count = entityManager.createQuery(jpql, Long.class)
                .setParameter("studentId", studentId)
                .setParameter("sectionId", sectionId)
                .getSingleResult();
        return count != null && count > 0;
    }

    @Override
    public List<Enrollment> findByStudentAndSemester(Long studentId, Long semesterId) {
        String jpql = "SELECT e FROM Enrollment e WHERE e.student.personId = :studentId AND e.section.semester.semesterId = :semesterId";
        return entityManager.createQuery(jpql, Enrollment.class)
                .setParameter("studentId", studentId)
                .setParameter("semesterId", semesterId)
                .getResultList();
    }

    @Override
    public long countBySection(Long sectionId) {
        String jpql = "SELECT COUNT(e) FROM Enrollment e WHERE e.section.sectionId = :sectionId";
        Long count = entityManager.createQuery(jpql, Long.class)
                .setParameter("sectionId", sectionId)
                .getSingleResult();
        return count != null ? count : 0L;
    }
}
