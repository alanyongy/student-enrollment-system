package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.dao.StudentDAO;
import com.example.CourseRegistrationSystem.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class StudentServiceImpl implements StudentService{

    private final StudentDAO studentDAO;

    @Autowired
    public StudentServiceImpl(StudentDAO studentDAO){
        this.studentDAO = studentDAO;
    }

    @Override
    public List<Student> findAll() {

        return studentDAO.findAll();
    }

    @Override
    public Optional<Student> findById(Long id) {
        return studentDAO.findById(id);
    }

    @Override
    public Student save(Student student) {
        return studentDAO.save(student);
    }

    @Override
    public void delete(Student student) {
        studentDAO.delete(student);
    }
}
