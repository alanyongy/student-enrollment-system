package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Admission;
import com.example.CourseRegistrationSystem.entity.Student;
import com.example.CourseRegistrationSystem.entity.Program;
import java.util.List;

public interface AdmissionDAO {
    Admission admitStudentToProgram(Student student, Program program);
    void removeStudentFromProgram(Student student, Program program);
    List<Admission> findAdmissionsByStudent(Student student);
    List<Admission> findAdmissionsByProgram(Program program);
    List<Admission> getAllAdmissions(int page, int size, String sortBy, String direction);
    Admission getById(Long id);
}

