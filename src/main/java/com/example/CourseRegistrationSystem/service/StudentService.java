package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.entity.Student;

import java.util.List;


public interface StudentService {

    List<Student> getStudents();

    Student getStudent(Long id);

    Student createStudent(Student student);

    Student updateStudent(Long id, Student student);

    void deleteStudent(Long id);
}
