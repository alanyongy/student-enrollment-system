package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.entity.Course;
import com.example.CourseRegistrationSystem.repository.CourseRepository;
import com.example.CourseRegistrationSystem.dto.CourseDTO;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

// https://www.javaguides.net/2025/03/spring-boot-architecture.html
// https://medium.com/@kalanamalshan98/building-the-service-layer-in-spring-boot-dbbc900b0853


@Service
public class CourseService {
    
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;
     
    public CourseService(CourseRepository courseRepository, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }    


    // List courses
    // GET /api/admin/courses
    public List<CourseDTO> listCourses() {
        
        return courseRepository.findAll()
                .stream()
                .map(i -> modelMapper.map(i, CourseDTO.class)) // map entity to dto
                .toList();
    }


    // Get course
    // GET /api/admin/courses/{id}
    public CourseDTO getCourse(Long id) {
        Course entity = courseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Course not found.")); // if id doesn't exist (handles Optional)
        return modelMapper.map(entity, CourseDTO.class);            // map entity to dto
    }


    // Create course
    // POST /api/admin/courses
    public CourseDTO createCourse(CourseDTO dto) {
        Course entity = modelMapper.map(dto, Course.class); // make entity from dto
        Course saved = courseRepository.save(entity);                       // save entity in repo
        return modelMapper.map(saved, CourseDTO.class);         // turn to dto again
    }


    // Update course
    // PUT /api/admin/courses/{id}
    public CourseDTO updateCourse(Long id, CourseDTO dto) {
        Course entity = courseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Course not found.")); // ensure entity exists
        
        modelMapper.map(dto, entity);                                           // copy dto onto entity
        Course updated = courseRepository.save(entity);                 // save changes
        return modelMapper.map(updated, CourseDTO.class);   // return updated dto
    }


    // Delete course
    // DELETE /api/admin/courses/{id}
    public boolean deleteCourse(Long id) {
        if(!courseRepository.existsById(id)) return false;  // can't find course
        courseRepository.deleteById(id);                    // delete from db
        return true;                    
    }    


}
