package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Course;
import com.example.CourseRegistrationSystem.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CourseDAOImpl implements CourseDAO{

    private final EntityManager entityManager;


    @Autowired
    public CourseDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }
    @Override
    public List<Course> findAll() {
        TypedQuery<Course> query = entityManager.createQuery("FROM Course", Course.class);

        return query.getResultList();
    }

    @Override
    public Optional<Course> findById(Long id) {

        Course course = entityManager.find(Course.class, id);
        return Optional.ofNullable(course);
    }

    @Override
    public Course save(Course course) {
        return entityManager.merge(course);
    }

    @Override
    public void delete(Course course) {
        entityManager.remove(course);
    }
}
