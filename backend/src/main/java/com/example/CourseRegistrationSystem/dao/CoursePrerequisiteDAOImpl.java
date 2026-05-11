package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.CoursePrerequisite;
import com.example.CourseRegistrationSystem.entity.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CoursePrerequisiteDAOImpl implements CoursePrerequisiteDAO {

    private final EntityManager entityManager;

    @Autowired
    public CoursePrerequisiteDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void addPrerequisite(Course course, Course prerequisiteCourse) {
        CoursePrerequisite cp = new CoursePrerequisite();
        cp.setCourse(course);
        cp.setPrerequisiteCourse(prerequisiteCourse);
        entityManager.persist(cp);
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

    @Override
    public List<CoursePrerequisite> findAll(int page, int size, String sortBy, String direction) {
    
        List<String> allowedSortFields = List.of("prerequisiteId");
    
        if (!allowedSortFields.contains(sortBy)) {
            sortBy = "prerequisiteId";
        }
    
        if (!direction.equalsIgnoreCase("asc") && !direction.equalsIgnoreCase("desc")) {
            direction = "asc";
        }
    
        String jpql = """
            SELECT DISTINCT cp
            FROM CoursePrerequisite cp
            JOIN FETCH cp.course
            JOIN FETCH cp.prerequisiteCourse
            ORDER BY cp.""" + sortBy + " " + direction;
    
        TypedQuery<CoursePrerequisite> query = entityManager.createQuery(jpql, CoursePrerequisite.class);
    
        query.setFirstResult(page * size);
        query.setMaxResults(size);
    
        List<CoursePrerequisite> result = query.getResultList();
    
        result.forEach(cp -> {
            if (cp.getCourse() != null) cp.getCourse().getCourseNumber();
            if (cp.getPrerequisiteCourse() != null) cp.getPrerequisiteCourse().getCourseNumber();
        });
    
        return result;
    }

    @Override
    public Optional<CoursePrerequisite> findById(Long id) {
        CoursePrerequisite cp = entityManager.find(CoursePrerequisite.class, id);
        return Optional.ofNullable(cp);
    }

    @Override
    public CoursePrerequisite save(CoursePrerequisite cp) {
        return entityManager.merge(cp);
    }

    @Override
    public void delete(CoursePrerequisite cp) {
        entityManager.remove(cp);
    }
}

