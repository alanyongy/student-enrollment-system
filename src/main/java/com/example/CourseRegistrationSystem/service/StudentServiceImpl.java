package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.dao.StudentDAO;
import com.example.CourseRegistrationSystem.entity.Student;

import com.example.CourseRegistrationSystem.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Service
public class StudentServiceImpl implements StudentService {

    private final StudentDAO studentDAO;

    public StudentServiceImpl(StudentDAO studentDAO) {
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

        return studentDAO.save(student);
    }

    @Transactional
    @Override
    public Student updateStudent(Long id, Student student) {

        Student existingStudent = studentDAO.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found with id " + id));

        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setEnrollmentYear(student.getEnrollmentYear());

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
