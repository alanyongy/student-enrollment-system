package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.entity.Instructor;

import java.util.List;

public interface InstructorService {

    List<Instructor> getInstructors(int page, int size, String sortBy, String direction);

    Instructor getInstructor(Long id);

    Instructor createInstructor(Instructor instructor);

    Instructor updateInstructor(Long id, Instructor instructor);

    void deleteInstructor(Long id);
}
