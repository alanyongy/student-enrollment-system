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
    public List<Student> getStudents(int page, int size, String sortBy, String direction) {
        return studentDAO.findAll(page, size, sortBy, direction);
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
    
        Student existing = studentDAO.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found with id " + id));
    
        // base fields
        existing.setFirstName(
                student.getFirstName() != null ? student.getFirstName() : existing.getFirstName()
        );
    
        existing.setMiddleName(
                student.getMiddleName() != null ? student.getMiddleName() : existing.getMiddleName()
        );
    
        existing.setLastName(
                student.getLastName() != null ? student.getLastName() : existing.getLastName()
        );
    
        existing.setEmail(
                student.getEmail() != null ? student.getEmail() : existing.getEmail()
        );
    
        existing.setPassword(
                student.getPassword() != null ? student.getPassword() : existing.getPassword()
        );
    
        existing.setPhoneNumber(
                student.getPhoneNumber() != null ? student.getPhoneNumber() : existing.getPhoneNumber()
        );
    
        existing.setDateOfBirth(
                student.getDateOfBirth() != null ? student.getDateOfBirth() : existing.getDateOfBirth()
        );
    
        // undergrad-specific fields
        if (existing instanceof Undergrad existingUg && student instanceof Undergrad incomingUg) {
    
            existingUg.setAcademicStatus(
                    incomingUg.getAcademicStatus() != null
                            ? incomingUg.getAcademicStatus()
                            : existingUg.getAcademicStatus()
            );
    
            existingUg.setCreditsEarned(
                    incomingUg.getCreditsEarned() != null
                            ? incomingUg.getCreditsEarned()
                            : existingUg.getCreditsEarned()
            );
    
            existingUg.setEnrollmentYear(
                    incomingUg.getEnrollmentYear() != null
                            ? incomingUg.getEnrollmentYear()
                            : existingUg.getEnrollmentYear()
            );
        }
    
        return studentDAO.save(existing);
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
