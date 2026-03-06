package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.dao.InstructorDAO;
import com.example.CourseRegistrationSystem.entity.Instructor;
import com.example.CourseRegistrationSystem.entity.Student;

import java.util.List;
import java.util.Optional;

public class InstructorServiceImpl implements InstructorService{

    private final InstructorDAO instructorDAO;

    public InstructorServiceImpl(InstructorDAO instructorDAO){
        this.instructorDAO = instructorDAO;
    }
    @Override
    public List<Instructor> findAll() {
        return List.of();
    }

    @Override
    public Optional<Instructor> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Student save(Instructor instructor) {
        return null;
    }

    @Override
    public void delete(Instructor instructor) {

    }
}
