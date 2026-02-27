package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.entity.Instructor;
import com.example.CourseRegistrationSystem.repository.InstructorRepository;
import com.example.CourseRegistrationSystem.dto.InstructorDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

// https://www.javaguides.net/2025/03/spring-boot-architecture.html
// https://medium.com/@kalanamalshan98/building-the-service-layer-in-spring-boot-dbbc900b0853

@Service
public class InstructorService {
    
    private InstructorRepository instructorRepository;
    private ModelMapper modelMapper;


    

}
