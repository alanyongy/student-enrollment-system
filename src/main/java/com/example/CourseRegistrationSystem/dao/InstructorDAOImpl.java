package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Instructor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InstructorDAOImpl implements InstructorDAO{

    private final EntityManager entityManager;


    @Autowired
    public InstructorDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }
    @Override
    public List<Instructor> findAll() {
        TypedQuery<Instructor> query = entityManager
                .createQuery("FROM Instructor", Instructor.class);
        return query.getResultList();
    }

    @Override
    public Optional<Instructor> findById(Long id) {
        Instructor instructor = entityManager.find(Instructor.class, id);
        return Optional.ofNullable(instructor);
    }

    @Override
    public Instructor save(Instructor instructor) {
        return entityManager.merge(instructor);
    }

    @Override
    public void delete(Instructor instructor) {
        entityManager.remove(instructor);
    }
}
