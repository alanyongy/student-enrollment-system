package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Program;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class ProgramDAOImpl implements ProgramDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Program> findAll() {
        return entityManager.createQuery("FROM Program", Program.class).getResultList();
    }

    @Override
    public Program findById(Long id) {
        return entityManager.find(Program.class, id);
    }

    @Override
    public Program save(Program program) {
        entityManager.persist(program);
        return program;
    }

    @Override
    public Program update(Program program) {
        return entityManager.merge(program);
    }

    @Override
    public void delete(Long id) {
        Program program = findById(id);
        if (program != null) {
            entityManager.remove(program);
        }
    }
}

