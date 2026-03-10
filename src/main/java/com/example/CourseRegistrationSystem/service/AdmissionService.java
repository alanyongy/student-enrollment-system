package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.entity.Admission;
import com.example.CourseRegistrationSystem.entity.Student;
import com.example.CourseRegistrationSystem.entity.Program;
import java.util.List;

public interface AdmissionService {
    Admission admitStudentToProgram(Student student, Program program);
    void removeStudentFromProgram(Student student, Program program);
    List<Admission> findAdmissionsByStudent(Student student);
    List<Admission> findAdmissionsByProgram(Program program);
}

