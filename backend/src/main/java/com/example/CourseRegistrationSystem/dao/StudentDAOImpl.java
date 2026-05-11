package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Semester;
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
    public List<Student> findAll(int page, int size, String sortBy, String direction) {
        
        // for sorting validation
        List<String> allowedSortFields = List.of("personId", "firstName", "middleName", "lastName", "email", "phoneNumber", "dateOfBirth", "enrollmentYear", "academicStatus", "creditsEarned"); 
            // did not include password or role, or gpa (transient)

        if (!allowedSortFields.contains(sortBy)) {
            sortBy = "personId"; // default sort field
        }

        if (!direction.equalsIgnoreCase("asc") && !direction.equalsIgnoreCase("desc")) {
            direction = "asc"; // default direction
        }

        String jpql = "FROM Student s ORDER BY s." + sortBy + " " + direction;
        
        TypedQuery<Student> query = entityManager.createQuery(jpql, Student.class);
        
        // pagination
        query.setFirstResult(page * size);
        query.setMaxResults(size);
                
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
