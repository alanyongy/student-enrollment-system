package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.CoursePrerequisite;
import com.example.CourseRegistrationSystem.entity.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CoursePrerequisiteDAOImpl implements CoursePrerequisiteDAO {

    private final EntityManager entityManager;

    @Autowired
    public CoursePrerequisiteDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public CoursePrerequisite addPrerequisite(Course course, Course prerequisiteCourse) {
        CoursePrerequisite cp = new CoursePrerequisite();
        cp.setCourse(course);
        cp.setPrerequisiteCourse(prerequisiteCourse);
        entityManager.persist(cp);
        return cp;
    }

    @Override
    public void removePrerequisite(Course course, Course prerequisiteCourse) {
        TypedQuery<CoursePrerequisite> query = entityManager.createQuery(
            "FROM CoursePrerequisite cp WHERE cp.course = :course AND cp.prerequisiteCourse = :prereq",
            CoursePrerequisite.class);
        query.setParameter("course", course);
        query.setParameter("prereq", prerequisiteCourse);
        List<CoursePrerequisite> cps = query.getResultList();
        for (CoursePrerequisite cp : cps) {
            entityManager.remove(cp);
        }
    }

    @Override
    public List<CoursePrerequisite> getPrerequisites(Course course) {
        TypedQuery<CoursePrerequisite> query = entityManager.createQuery(
            "FROM CoursePrerequisite cp WHERE cp.course = :course",
            CoursePrerequisite.class);
        query.setParameter("course", course);
        return query.getResultList();
    }
}

