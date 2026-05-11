package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Course;
import com.example.CourseRegistrationSystem.entity.Program;
import com.example.CourseRegistrationSystem.entity.ProgramCourseAccess;
import com.example.CourseRegistrationSystem.exception.ResourceNotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ProgramCourseAccessDAOImpl implements ProgramCourseAccessDAO{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addCourseToProgram(long programId, long courseId) {
        ProgramCourseAccess access = new ProgramCourseAccess();
        Program program = entityManager.find(Program.class, programId);
        access.setProgram(program);
        Course course  =  entityManager.find(Course.class, courseId);
        access.setCourse(course);
        entityManager.persist(access);
    }

    @Override
    @Transactional
    public void removeCourseFromProgram(ProgramCourseAccess access) {

        ProgramCourseAccess managed = entityManager.find(
                ProgramCourseAccess.class,
                access.getAccessId()
        );

        if (managed == null) {
            throw new ResourceNotFoundException(
                    "ProgramCourseAccess not found with id " + access.getAccessId()
            );
        }

        entityManager.remove(managed);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isCourseInProgram(long programId, long courseId) {
        String jpql = "SELECT COUNT(p) FROM ProgramCourseAccess p WHERE p.program.programId = :programId AND p.course.courseId = :courseId";
        Long count = entityManager.createQuery(jpql, Long.class).getSingleResult();
        return count > 0;
    }

    @Override
    public Optional<ProgramCourseAccess> findById(Long id) {

        ProgramCourseAccess entity = entityManager.find(
                ProgramCourseAccess.class,
                id
        );

        return Optional.ofNullable(entity);
    }

    @Override
    @Transactional
    public ProgramCourseAccess save(ProgramCourseAccess entity) {
        return entityManager.merge(entity);
    }

    @Override
    public List<ProgramCourseAccess> findAll(int page, int size, String sortBy, String direction) {

        List<String> allowedSortFields = List.of("accessId");

        if (!allowedSortFields.contains(sortBy)) {
            sortBy = "accessId";
        }

        if (!direction.equalsIgnoreCase("asc") && !direction.equalsIgnoreCase("desc")) {
            direction = "asc";
        }

        String jpql = """
            SELECT DISTINCT pca
            FROM ProgramCourseAccess pca
            LEFT JOIN FETCH pca.program
            LEFT JOIN FETCH pca.course
            ORDER BY pca.""" + sortBy + " " + direction;

        TypedQuery<ProgramCourseAccess> query = entityManager.createQuery(jpql, ProgramCourseAccess.class);

        query.setFirstResult(page * size);
        query.setMaxResults(size);

        List<ProgramCourseAccess> result = query.getResultList();

        result.forEach(pca -> {
            if (pca.getProgram() != null) pca.getProgram().getProgramName();
            if (pca.getCourse() != null) pca.getCourse().getCourseNumber();
        });

        return result;
    }
}
