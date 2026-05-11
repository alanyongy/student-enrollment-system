package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Section;
import com.example.CourseRegistrationSystem.entity.Semester;
import com.example.CourseRegistrationSystem.entity.SemesterSection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class SemesterSectionDAOImpl implements SemesterSectionDAO{

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void addSectionToSemester(long semesterId, long sectionId) {
        SemesterSection semesterSection = new SemesterSection();
        Semester semester = entityManager.find(Semester.class, semesterId);
        semesterSection.setSemester(semester);
        Section section = entityManager.find(Section.class, sectionId);
        semesterSection.setSection(section);
        entityManager.persist(semesterSection);
    }

    @Override
    public void removeSectionFromSemester(long semesterId, long sectionId) {
        String jpql = "SELECT ss FROM SemesterSection ss WHERE ss.semester.semesterId = :semesterId AND ss.section.sectionId = :sectionId";
        SemesterSection semesterSection = entityManager.createQuery(jpql, SemesterSection.class)
                .setParameter("semesterId", semesterId)
                .setParameter("sectionId", sectionId)
                .getSingleResult();
        if (semesterSection != null) {
            entityManager.remove(semesterSection);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isSectionInSemester(long semesterId, long sectionId) {
        String jpql = "SELECT COUNT(ss) FROM SemesterSection ss WHERE ss.semester.semesterId = :semesterId AND ss.section.sectionId = :sectionId";
        Long count = entityManager.createQuery(jpql, Long.class)
                .setParameter("semesterId", semesterId)
                .setParameter("sectionId", sectionId)
                .getSingleResult();
        return count > 0;
    }
}
