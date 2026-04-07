package com.example.CourseRegistrationSystem.controller;

import com.example.CourseRegistrationSystem.dto.StudentProfileDTO;
import com.example.CourseRegistrationSystem.entity.Person;
import com.example.CourseRegistrationSystem.entity.Student;
import com.example.CourseRegistrationSystem.entity.Undergrad;
import com.example.CourseRegistrationSystem.exception.ResourceNotFoundException;
import com.example.CourseRegistrationSystem.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/me")
public class StudentProfileController {

    private final UserRepository userRepository;

    public StudentProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/profile")
    public ResponseEntity<StudentProfileDTO> getProfile(Authentication authentication) {
        String email = authentication.getName();

        Person person = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        if (!(person instanceof Student student)) {
            throw new ResourceNotFoundException("Current user is not a student");
        }

        StudentProfileDTO dto = mapToProfileDto(student);
        return ResponseEntity.ok(dto);
    }

    private StudentProfileDTO mapToProfileDto(Student student) {
        StudentProfileDTO dto = new StudentProfileDTO();
        dto.setId(student.getPersonId());
        dto.setFirstName(student.getFirstName());
        dto.setMiddleName(student.getMiddleName());
        dto.setLastName(student.getLastName());
        dto.setEmail(student.getEmail());
        dto.setPhoneNumber(student.getPhoneNumber());
        dto.setDateOfBirth(student.getDateOfBirth());

        dto.setEnrollmentYear(student.getEnrollmentYear());
        dto.setAcademicStatus(student.getAcademicStatus());
        dto.setCreditsEarned(student.getCreditsEarned());
        dto.setGpa(student.getGpa());

        if (student instanceof Undergrad undergrad) {
            dto.setYearOfStudy(undergrad.getYearOfStudy());
        }

        return dto;
    }
}
