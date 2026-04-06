package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Program;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class ProgramDAOImpl implements ProgramDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Program> findAll(int page, int size, String sortBy, String direction) {

        // sorting and validation
        List<String> allowedSortFields = List.of("programId", "programName", "description", "department", "programType");

        if (!allowedSortFields.contains(sortBy)) {
            sortBy = "programId"; // default sort field
        }

        if (!direction.equalsIgnoreCase("asc") && !direction.equalsIgnoreCase("desc")) {
            direction = "asc"; // default sort direction
        }

        String jpql = "FROM Program p ORDER BY p." + sortBy + " " + direction.toUpperCase();

        TypedQuery<Program> query = entityManager.createQuery(jpql, Program.class);

        // pagination
        query.setFirstResult(page * size);
        query.setMaxResults(size);

        return query.getResultList();
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

