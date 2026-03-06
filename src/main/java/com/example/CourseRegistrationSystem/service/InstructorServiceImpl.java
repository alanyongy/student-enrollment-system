package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.dao.InstructorDAO;
import com.example.CourseRegistrationSystem.entity.Instructor;
import com.example.CourseRegistrationSystem.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InstructorServiceImpl implements InstructorService{

    private final InstructorDAO instructorDAO;

    @Autowired
    public InstructorServiceImpl(InstructorDAO instructorDAO){
        this.instructorDAO = instructorDAO;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Instructor> getInstructors() {
        return instructorDAO.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Instructor getInstructor(Long id) {

        return instructorDAO.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Instructor not found with id " + id));
    }

    @Transactional
    @Override
    public Instructor createInstructor(Instructor instructor) {
        instructor.setPersonId(null);
        return instructorDAO.save(instructor);
    }

    @Transactional
    @Override
    public Instructor updateInstructor(Long id, Instructor instructor) {

        Instructor existingInstructor = instructorDAO.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Instructor not found with id " + id));


        instructor.setPersonId(existingInstructor.getPersonId());


        existingInstructor.setFirstName(instructor.getFirstName());
        existingInstructor.setMiddleName(instructor.getMiddleName());
        existingInstructor.setLastName(instructor.getLastName());
        existingInstructor.setEmail(instructor.getEmail());
        existingInstructor.setPassword(instructor.getPassword());
        existingInstructor.setPhoneNumber(instructor.getPhoneNumber());
        existingInstructor.setDateOfBirth(instructor.getDateOfBirth());
        existingInstructor.setYearsOfExperience(instructor.getYearsOfExperience());
        existingInstructor.setExpertiseTopics(instructor.getExpertiseTopics());

        return instructorDAO.save(existingInstructor);
    }

    @Transactional
    @Override
    public void deleteInstructor(Long id) {

        Instructor instructor = instructorDAO.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Instructor not found with id " + id));

        instructorDAO.delete(instructor);
    }
}
