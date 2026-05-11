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
    public List<Instructor> findAll(int page, int size, String sortBy, String direction) {
        
        // for sorting validation
        List<String> allowedSortFields = List.of("personId", "firstName", "middleName", "lastName", "email", "phoneNumber", "dateOfBirth", "yearsOfExperience", "expertiseTopics"); 
            // did not include password or role

        if (!allowedSortFields.contains(sortBy)) {
            sortBy = "personId"; // default sort field
        }

        if (!direction.equalsIgnoreCase("asc") && !direction.equalsIgnoreCase("desc")) {
            direction = "asc"; // default direction
        }

        String jpql = "FROM Instructor i ORDER BY i." + sortBy + " " + direction;
        
        TypedQuery<Instructor> query = entityManager.createQuery(jpql, Instructor.class);
        
        // pagination
        query.setFirstResult(page * size);
        query.setMaxResults(size);
                
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
