package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Course;
import com.example.CourseRegistrationSystem.entity.Program;
import com.example.CourseRegistrationSystem.entity.ProgramCourseAccess;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
    public void removeCourseFromProgram(long programId, long courseId) {
        String jpql = "SELECT p FROM ProgramCourseAccess p WHERE p.program.programId = :programId AND p.course.courseId = :courseId";
        ProgramCourseAccess access = entityManager.createQuery(jpql, ProgramCourseAccess.class)
                .setParameter("programId", programId)
                .setParameter("courseId", courseId)
                .getSingleResult();
        if (access != null) {
            entityManager.remove(access);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isCourseInProgram(long programId, long courseId) {
        String jpql = "SELECT COUNT(p) FROM ProgramCourseAccess p WHERE p.program.programId = :programId AND p.course.courseId = :courseId";
        Long count = entityManager.createQuery(jpql, Long.class).getSingleResult();
        return count > 0;
    }
}
