package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.entity.Course;
import com.example.CourseRegistrationSystem.repository.CourseRepository;
import com.example.CourseRegistrationSystem.repository.InstructorRepository;
import com.example.CourseRegistrationSystem.dto.CourseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

// https://www.javaguides.net/2025/03/spring-boot-architecture.html
// https://medium.com/@kalanamalshan98/building-the-service-layer-in-spring-boot-dbbc900b0853


@Service
public class CourseService {
    
    private CourseRepository courseRepository;
    private ModelMapper modelMapper;
     

    // List courses
    // GET /api/admin/courses



    // Get course
    // GET /api/admin/courses/{id}



    // Create course
    // POST /api/admin/courses




    // Update course
    // PUT /api/admin/courses/{id}




    // Delete course
    // DELETE /api/admin/courses/{id}



}
