package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Department;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class DepartmentDAOImpl implements DepartmentDAO {

    private final EntityManager entityManager;

    @Autowired
    public DepartmentDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Department> findAll(int page, int size, String sortBy, String direction) {
        
        // for validation
        List<String> allowedSortFields = List.of("deptId", "deptName", "deptEmail", "phoneNumber", "officeLocation");

        if (!allowedSortFields.contains(sortBy)) {
            sortBy = "deptId"; // default sort field
        }

        if (!direction.equalsIgnoreCase("asc") && !direction.equalsIgnoreCase("desc")) {
            direction = "asc"; // default direction
        }

        String jpql = "FROM Department d ORDER BY d." + sortBy + " " + direction;
        
        
        TypedQuery<Department> query = entityManager.createQuery(jpql, Department.class);
        
        // pagination
        query.setFirstResult(page * size);
        query.setMaxResults(size);

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


