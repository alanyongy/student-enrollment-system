package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.entity.Instructor;

import java.util.List;

public interface InstructorService {

    List<Instructor> getInstructors();

    Instructor getInstructor(Long id);

    Instructor createInstructor(Instructor instructor);

    Instructor updateInstructor(Long id, Instructor instructor);

    void deleteInstructor(Long id);
}
