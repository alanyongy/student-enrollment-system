package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.dao.AdmissionDAO;
import com.example.CourseRegistrationSystem.entity.Admission;
import com.example.CourseRegistrationSystem.entity.Student;
import com.example.CourseRegistrationSystem.entity.Program;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdmissionServiceImpl implements AdmissionService {

    private final AdmissionDAO admissionDAO;

    @Autowired
    public AdmissionServiceImpl(AdmissionDAO admissionDAO) {
        this.admissionDAO = admissionDAO;
    }

    @Override
    public Admission admitStudentToProgram(Student student, Program program) {
        return admissionDAO.admitStudentToProgram(student, program);
    }

    @Override
    public void removeStudentFromProgram(Student student, Program program) {
        admissionDAO.removeStudentFromProgram(student, program);
    }

    @Override
    public List<Admission> findAdmissionsByStudent(Student student) {
        return admissionDAO.findAdmissionsByStudent(student);
    }

    @Override
    public List<Admission> findAdmissionsByProgram(Program program) {
        return admissionDAO.findAdmissionsByProgram(program);
    }

    @Override
    public List<Admission> getAllAdmissions() {
        return admissionDAO.getAllAdmissions();
    }

    @Override
    public Admission getById(Long id) {
        return admissionDAO.getById(id);
    }
}

