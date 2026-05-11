package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Instructor;
import com.example.CourseRegistrationSystem.entity.Section;
import com.example.CourseRegistrationSystem.exception.ResourceNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class SectionDAOImpl implements SectionDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Section> findAll(int page, int size, String sortBy, String direction) {
        
        // sorting and validation
        List<String> allowedSortFields = List.of("sectionId", "scheduleTime", "location", "capacity"); 
            // didn't include course or instructor as they're objects

        if (!allowedSortFields.contains(sortBy)) {
            sortBy = "sectionId"; // default sort field
        }

        if (!direction.equalsIgnoreCase("asc") && !direction.equalsIgnoreCase("desc")) {
            direction = "asc"; // default sort direction
        }

        String jpql = """
            SELECT DISTINCT s
            FROM Section s
            LEFT JOIN FETCH s.semester
            LEFT JOIN FETCH s.instructor
            LEFT JOIN FETCH s.course
            ORDER BY s.""" + sortBy + " " + direction;

        TypedQuery<Section> query = entityManager.createQuery(jpql, Section.class);

        // pagination
        query.setFirstResult(page * size);
        query.setMaxResults(size);

        return query.getResultList();
    }

    @Override
    public Section findById(Long id) {
        return entityManager.find(Section.class, id);
    }

    @Override
    public Section save(Section section) {
        entityManager.persist(section);
        return section;
    }

    @Override
    public Section update(Section section) {
        return entityManager.merge(section);
    }

    @Override
    public void delete(Long id) {
        Section section = findById(id);
        if (section != null) {
            entityManager.remove(section);
        }
    }

    @Override
    public void assignInstructor(Long sectionId, Long instructorId) {
        Section section = findById(sectionId);
        if (section == null) throw new ResourceNotFoundException("Section not found");
        Instructor instructor = entityManager.find(Instructor.class, instructorId);
        if (instructor == null) throw new ResourceNotFoundException("Instructor not found");
        section.setInstructor(instructor);
        entityManager.merge(section);
    }

    @Override
    public void removeInstructor(Long sectionId) {
        Section section = findById(sectionId);
        if (section == null) throw new RuntimeException("Section not found");
        section.setInstructor(null);
        entityManager.merge(section);
    }

    @Override
    public List<Section> findBySemesterId(Long semesterId) {
        String jpql = "SELECT ss.section FROM SemesterSection ss WHERE ss.semester.semesterId = :semesterId";

        return entityManager.createQuery(jpql, Section.class)
                .setParameter("semesterId", semesterId)
                .getResultList();
    }

}
