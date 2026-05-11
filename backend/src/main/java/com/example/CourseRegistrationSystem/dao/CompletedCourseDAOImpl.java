package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.CompletedCourse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

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

@Override
public List<CompletedCourse> findAll(int page, int size, String sortBy, String direction) {

    List<String> allowedSortFields = List.of(
            "completedCourseId",
            "grade"
            // add other scalar fields if you have them
    );

    if (!allowedSortFields.contains(sortBy)) {
        sortBy = "completedCourseId";
    }

    if (!direction.equalsIgnoreCase("asc") && !direction.equalsIgnoreCase("desc")) {
        direction = "asc";
    }

    String jpql = """
        SELECT DISTINCT cc
        FROM CompletedCourse cc
        LEFT JOIN FETCH cc.student
        LEFT JOIN FETCH cc.course
        ORDER BY cc.""" + sortBy + " " + direction;

    TypedQuery<CompletedCourse> query = entityManager.createQuery(jpql, CompletedCourse.class);

    query.setFirstResult(page * size);
    query.setMaxResults(size);

    List<CompletedCourse> result = query.getResultList();

    result.forEach(cc -> {
        if (cc.getStudent() != null) {
            cc.getStudent().getFirstName();
        }
        if (cc.getCourse() != null) {
            cc.getCourse().getCourseNumber();
        }
    });

    return result;
}

    @Override
    public CompletedCourse findById(Long id) {
        return entityManager.find(CompletedCourse.class, id);
    }

    @Override
    public void delete(CompletedCourse completion) {
        entityManager.remove(
            entityManager.contains(completion)
                ? completion
                : entityManager.merge(completion)
        );
    }
}

