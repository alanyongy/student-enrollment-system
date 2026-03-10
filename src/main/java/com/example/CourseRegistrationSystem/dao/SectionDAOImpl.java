package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Instructor;
import com.example.CourseRegistrationSystem.entity.Section;
import com.example.CourseRegistrationSystem.exception.ResourceNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class SectionDAOImpl implements SectionDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Section> findAll() {
        return entityManager.createQuery("FROM Section", Section.class).getResultList();
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
}
