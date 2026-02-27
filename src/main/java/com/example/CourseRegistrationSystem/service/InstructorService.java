package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.entity.Instructor;
import com.example.CourseRegistrationSystem.repository.InstructorRepository;
import com.example.CourseRegistrationSystem.dto.InstructorDTO;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

// https://www.javaguides.net/2025/03/spring-boot-architecture.html
// https://medium.com/@kalanamalshan98/building-the-service-layer-in-spring-boot-dbbc900b0853

@Service
public class InstructorService {
    
    private InstructorRepository instructorRepository;
    private ModelMapper modelMapper;

    public InstructorService(InstructorRepository instructorRepository, ModelMapper modelMapper) {
        this.instructorRepository = instructorRepository;
        this.modelMapper = modelMapper;
    }

    // List instructors (last name ascending)
    // GET /api/admin/instructors
    // GET /api/admin/instructors?sort=lastName,asc
    public List<InstructorDTO> listInstructors() {
        
        return instructorRepository.findAll(Sort.by(Sort.Direction.ASC, "lastName"))
                .stream()
                .map(i -> modelMapper.map(i, InstructorDTO.class)) // map entity to dto
                .toList();
    }
    
    // Get instructor
    // GET /api/admin/instructors/{id}
    public InstructorDTO getInstructor(Long id) {
        Instructor entity = instructorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Instructor not found.")); // if id doesn't exist (handles Optional)
        return modelMapper.map(entity, InstructorDTO.class);            // map entity to dto
    }


    // Create instructor
    // POST /api/admin/instructors
    public InstructorDTO createInstructor(InstructorDTO dto) {
        Instructor entity = modelMapper.map(dto, Instructor.class); // make entity from dto
        Instructor saved = instructorRepository.save(entity);                       // save entity in repo
        return modelMapper.map(saved, InstructorDTO.class);         // turn to dto again
    }


    // Update instructor
    // PUT /api/admin/instructors/{id}
    public InstructorDTO updateInstructor(Long id, InstructorDTO dto) {
        Instructor entity = instructorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Instructor not found.")); // ensure entity exists
        
        modelMapper.map(dto, entity);                                           // copy dto onto entity
        Instructor updated = instructorRepository.save(entity);                 // save changes
        return modelMapper.map(updated, InstructorDTO.class);   // return updated dto
    }


    // Delete instructor
    // DELETE /api/admin/instructors/{id}
    public boolean deleteInstructor(Long id) {
        if(!instructorRepository.existsById(id)) return false;  // can't find instructor
        instructorRepository.deleteById(id);                    // delete from db
        return true;                    
    }



}
