package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.CompletedCourse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CompletedCourseDAOImpl implements CompletedCourseDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CompletedCourse save(CompletedCourse completedCourse) {
        entityManager.persist(completedCourse);
        return completedCourse;
    }

    @Override
    public void deleteByStudentAndCourse(Long studentId, Long courseId) {
        entityManager.createQuery("DELETE FROM CompletedCourse cc WHERE cc.student.personId = :studentId AND cc.course.courseId = :courseId")
                .setParameter("studentId", studentId)
                .setParameter("courseId", courseId)
                .executeUpdate();
    }

    @Override
    public Optional<CompletedCourse> findByStudentAndCourse(Long studentId, Long courseId) {
        List<CompletedCourse> result = entityManager.createQuery("FROM CompletedCourse cc WHERE cc.student.personId = :studentId AND cc.course.courseId = :courseId", CompletedCourse.class)
                .setParameter("studentId", studentId)
                .setParameter("courseId", courseId)
                .getResultList();
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public List<CompletedCourse> findByStudentId(Long studentId) {
        return entityManager.createQuery("FROM CompletedCourse cc WHERE cc.student.personId = :studentId", CompletedCourse.class)
                .setParameter("studentId", studentId)
                .getResultList();
    }
}

