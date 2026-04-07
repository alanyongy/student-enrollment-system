package com.example.CourseRegistrationSystem.controller;

import com.example.CourseRegistrationSystem.dao.SemesterDAO;
import com.example.CourseRegistrationSystem.dto.SemesterDTO;
import com.example.CourseRegistrationSystem.entity.Person;
import com.example.CourseRegistrationSystem.entity.Student;
import com.example.CourseRegistrationSystem.entity.Semester;
import com.example.CourseRegistrationSystem.exception.ResourceNotFoundException;
import com.example.CourseRegistrationSystem.repository.UserRepository;
import com.example.CourseRegistrationSystem.service.SemesterService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/student")
public class StudentSemesterController {

    private final UserRepository userRepository;
    private final SemesterService semesterService;

    public StudentSemesterController(UserRepository userRepository, SemesterService semesterService) {
        this.userRepository = userRepository;
        this.semesterService = semesterService;
    }

    @GetMapping("/semesters")
    public ResponseEntity<List<SemesterDTO>> getSemesters(Authentication authentication) {
        Student student = getAuthenticatedStudent(authentication);

        List<SemesterDTO> semesters = semesterService.getAllSemesters(0, 10, "semesterId", "asc")
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(semesters);
    }



    private Student getAuthenticatedStudent(Authentication authentication) {
        String email = authentication.getName();

        Person person = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        if (!(person instanceof Student student)) {
            throw new ResourceNotFoundException("Current user is not a student");
        }

        return student;
    }

    private SemesterDTO mapToDto(Semester semester) {
        SemesterDTO dto = new SemesterDTO();
        dto.setSemesterId(semester.getSemesterId());
        dto.setTermName(semester.getTermName());
        dto.setStartDate(semester.getStartDate());
        dto.setEndDate(semester.getEndDate());
        return dto;
    }
}