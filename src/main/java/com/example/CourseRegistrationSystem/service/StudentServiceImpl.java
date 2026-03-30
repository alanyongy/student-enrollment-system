package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.dao.CourseDAO;
import com.example.CourseRegistrationSystem.dao.StudentDAO;
import com.example.CourseRegistrationSystem.entity.Student;
import com.example.CourseRegistrationSystem.entity.Undergrad;
import com.example.CourseRegistrationSystem.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentDAO studentDAO;

    // For Spring injection compatibility
    public StudentServiceImpl(StudentDAO studentDAO, CourseDAO courseDAO) {
        this.studentDAO = studentDAO;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Student> getStudents() {
        return studentDAO.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Student getStudent(Long id) {

        return studentDAO.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found with id " + id));
    }

    @Transactional
    @Override
    public Student createStudent(Student student) {

        student.setPersonId(null);

        // base defaults for ALL student subclasses so validation on Student passes
        if (student.getEnrollmentYear() == null) {
            student.setEnrollmentYear(Year.now().getValue());
        }
        if (student.getAcademicStatus() == null || student.getAcademicStatus().isBlank()) {
            student.setAcademicStatus("ACTIVE");
        }
        if (student.getCreditsEarned() == null) {
            student.setCreditsEarned(0);
        }

        // extra defaults only for Undergrad
        if (student instanceof Undergrad undergrad) {
            if (undergrad.getYearOfStudy() == null) {
                undergrad.setYearOfStudy(1);
            }
        }

        return studentDAO.save(student);
    }

    @Transactional
    @Override
    public Student updateStudent(Long id, Student student) {

        Student existingStudent = studentDAO.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found with id " + id));

        student.setPersonId(existingStudent.getPersonId());

        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setMiddleName(student.getMiddleName());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setPassword(student.getPassword());
        existingStudent.setPhoneNumber(student.getPhoneNumber());
        existingStudent.setDateOfBirth(student.getDateOfBirth());

        existingStudent.setEnrollmentYear(student.getEnrollmentYear());
        existingStudent.setAcademicStatus(student.getAcademicStatus());
        existingStudent.setCreditsEarned(student.getCreditsEarned());

        return studentDAO.save(existingStudent);
    }

    @Transactional
    @Override
    public void deleteStudent(Long id) {

        Student student = studentDAO.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found with id " + id));

        studentDAO.delete(student);
    }

}
