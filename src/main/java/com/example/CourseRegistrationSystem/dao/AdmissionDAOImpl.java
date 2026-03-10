package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Admission;
import com.example.CourseRegistrationSystem.entity.Student;
import com.example.CourseRegistrationSystem.entity.Program;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class AdmissionDAOImpl implements AdmissionDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Admission admitStudentToProgram(Student student, Program program) {
        Admission admission = new Admission();
        admission.setStudent(student);
        admission.setProgram(program);
        entityManager.persist(admission);
        return admission;
    }

    @Override
    public void removeStudentFromProgram(Student student, Program program) {
        String jpql = "SELECT a FROM Admission a WHERE a.student = :student AND a.program = :program";
        List<Admission> admissions = entityManager.createQuery(jpql, Admission.class)
                .setParameter("student", student)
                .setParameter("program", program)
                .getResultList();
        for (Admission admission : admissions) {
            entityManager.remove(admission);
        }
    }

    @Override
    public List<Admission> findAdmissionsByStudent(Student student) {
        String jpql = "SELECT a FROM Admission a WHERE a.student = :student";
        return entityManager.createQuery(jpql, Admission.class)
                .setParameter("student", student)
                .getResultList();
    }

    @Override
    public List<Admission> findAdmissionsByProgram(Program program) {
        String jpql = "SELECT a FROM Admission a WHERE a.program = :program";
        return entityManager.createQuery(jpql, Admission.class)
                .setParameter("program", program)
                .getResultList();
    }
}

