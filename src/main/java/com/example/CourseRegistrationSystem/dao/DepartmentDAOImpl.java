package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Department;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DepartmentDAOImpl implements DepartmentDAO {

    private final EntityManager entityManager;

    @Autowired
    public DepartmentDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Department> findAll() {
        TypedQuery<Department> query = entityManager.createQuery("FROM Department", Department.class);
        return query.getResultList();
    }

    @Override
    public Optional<Department> findById(Long id) {
        Department department = entityManager.find(Department.class, id);
        return Optional.ofNullable(department);
    }

    @Override
    public Department save(Department department) {
        if (department.getDeptId() == null) {
            entityManager.persist(department);
            return department;
        } else {
            return entityManager.merge(department);
        }
    }

    @Override
    public void delete(Department department) {
        entityManager.remove(department);
    }
}

