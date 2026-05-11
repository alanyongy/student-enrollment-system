package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Admission;
import com.example.CourseRegistrationSystem.entity.Student;
import com.example.CourseRegistrationSystem.entity.Program;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
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

    @Override
    public List<Admission> getAllAdmissions(int page, int size, String sortBy, String direction) {

        List<String> allowedSortFields = List.of(
                "admissionId",
                "status"
        );

        if (!allowedSortFields.contains(sortBy)) {
            sortBy = "admissionId";
        }

        if (!direction.equalsIgnoreCase("asc") && !direction.equalsIgnoreCase("desc")) {
            direction = "asc";
        }

        String jpql = """
            SELECT DISTINCT a
            FROM Admission a
            JOIN FETCH a.student
            JOIN FETCH a.program
            ORDER BY a.""" + sortBy + " " + direction;

        TypedQuery<Admission> query = entityManager.createQuery(jpql, Admission.class);

        query.setFirstResult(page * size);
        query.setMaxResults(size);

        List<Admission> result = query.getResultList();

        result.forEach(a -> {
            if (a.getStudent() != null) a.getStudent().getFirstName();
            if (a.getProgram() != null) a.getProgram().getProgramName();
        });

        return result;
    }

    @Override
    public Admission getById(Long admissionId) {
        String jpql =
            "SELECT a FROM Admission a " +
            "JOIN FETCH a.student " +
            "JOIN FETCH a.program " +
            "WHERE a.admissionId = :admissionId";

        return entityManager.createQuery(jpql, Admission.class)
                .setParameter("admissionId", admissionId)
                .getSingleResult();
    }
}

