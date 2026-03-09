package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Semester;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class SemesterDAOImpl implements SemesterDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Semester> findAll() {
        return entityManager.createQuery("FROM Semester", Semester.class).getResultList();
    }

    @Override
    public Semester findById(Long id) {
        return entityManager.find(Semester.class, id);
    }

    @Override
    public Semester save(Semester semester) {
        entityManager.persist(semester);
        return semester;
    }

    @Override
    public Semester update(Semester semester) {
        return entityManager.merge(semester);
    }

    @Override
    public void delete(Long id) {
        Semester semester = findById(id);
        if (semester != null) {
            entityManager.remove(semester);
        }
    }
}

