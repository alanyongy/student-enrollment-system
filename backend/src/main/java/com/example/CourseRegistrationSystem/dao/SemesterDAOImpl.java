package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Instructor;
import com.example.CourseRegistrationSystem.entity.Section;
import com.example.CourseRegistrationSystem.entity.Semester;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class SemesterDAOImpl implements SemesterDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Semester> findAll(int page, int size, String sortBy, String direction) {
    
        // for sorting validation
        List<String> allowedSortFields = List.of("semesterId", "termName", "startDate", "endDate"); 

        if (!allowedSortFields.contains(sortBy)) {
            sortBy = "semesterId"; // default sort field
        }

        if (!direction.equalsIgnoreCase("asc") && !direction.equalsIgnoreCase("desc")) {
            direction = "asc"; // default direction
        }

        String jpql = "FROM Semester s ORDER BY s." + sortBy + " " + direction;
        
        TypedQuery<Semester> query = entityManager.createQuery(jpql, Semester.class);
        
        // pagination
        query.setFirstResult(page * size);
        query.setMaxResults(size);
                
        return query.getResultList();
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

    @Override
    public List<Semester> findByStudentId(Long studentId) {
        String sql = """
        SELECT DISTINCT s.*
        FROM semesters s
        JOIN semester_sections ss ON ss.semester_id = s.semester_id
        JOIN enrollments e ON e.semester_section_id = ss.semester_section_id
        WHERE e.student_id = ?
        """;

        TypedQuery<Semester> query = entityManager.createQuery(sql, Semester.class);
        query.setParameter(1, studentId);
        return query.getResultList();
    }
}

