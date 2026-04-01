package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Course;
import com.example.CourseRegistrationSystem.entity.Department;
import com.example.CourseRegistrationSystem.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class CourseDAOImpl implements CourseDAO{

    private final EntityManager entityManager;

    @Autowired
    private DepartmentDAO departmentDAO;

    @Autowired
    public CourseDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }
    @Override
    public List<Course> findAll() {
        TypedQuery<Course> query = entityManager.createQuery("FROM Course", Course.class);

        return query.getResultList();
    }

    @Override
    public Optional<Course> findById(Long id) {

        Course course = entityManager.find(Course.class, id);
        return Optional.ofNullable(course);
    }

    @Override
    public Course save(Course course) {
        return entityManager.merge(course);
    }

    @Override
    public void delete(Course course) {
        entityManager.remove(course);
    }

    @Override
    public Course assignDepartment(Long courseId, Long deptId) {
        Course course = entityManager.find(Course.class, courseId);
        Department department = entityManager.find(Department.class, deptId);
        if (course == null || department == null) {
            throw new RuntimeException("Course or Department not found");
        }
        course.setDepartment(department);
        return entityManager.merge(course);
    }

    @Override
    public Course removeDepartment(Long courseId) {
        Course course = entityManager.find(Course.class, courseId);
        if (course == null) {
            throw new RuntimeException("Course not found");
        }
        course.setDepartment(null);
        return entityManager.merge(course);
    }

    @Override
    public List<Course> findBySemesterId(Long semesterId) {
        String jpql = "SELECT DISTINCT c FROM Course c " +
                    "JOIN c.sections s " +
                    "WHERE s.semester.semesterId = :semesterId";

        return entityManager.createQuery(jpql, Course.class)
                .setParameter("semesterId", semesterId)
                .getResultList();
    }

    @Override
    public List<Course> findAllBySemester(Long semesterId) {
        TypedQuery<Course> query = entityManager.createQuery(
            "SELECT DISTINCT c FROM Course c JOIN c.sections s WHERE s.semester.semesterId = :semesterId",
            Course.class
        );
        query.setParameter("semesterId", semesterId);
        return query.getResultList();
    }

    @Override
    public Optional<Course> findByIdAndSemester(Long courseId, Long semesterId) {
        TypedQuery<Course> query = entityManager.createQuery(
            "SELECT DISTINCT c FROM Course c JOIN c.sections s " +
            "WHERE c.courseId = :courseId AND s.semester.semesterId = :semesterId",
            Course.class
        );
        query.setParameter("courseId", courseId);
        query.setParameter("semesterId", semesterId);

        return query.getResultList().stream().findFirst();
    }
}
