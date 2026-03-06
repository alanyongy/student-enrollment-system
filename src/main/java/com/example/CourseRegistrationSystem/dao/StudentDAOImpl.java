package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class StudentDAOImpl implements StudentDAO {

    private final EntityManager entityManager;

    @Autowired
    public StudentDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<Student> findAll() {

        TypedQuery<Student> query =
                entityManager.createQuery("FROM Student", Student.class);

        return query.getResultList();
    }

    @Override
    public Optional<Student> findById(Long id) {

        Student student = entityManager.find(Student.class, id);

        return Optional.ofNullable(student);
    }


    @Override
    public Student save(Student student) {
        return entityManager.merge(student);
    }

    @Override
    public void delete(Student student) {
        entityManager.remove(student);
    }
}
