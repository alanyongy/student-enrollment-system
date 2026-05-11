package com.example.CourseRegistrationSystem.controller;

import com.example.CourseRegistrationSystem.dto.ApiResponse;
import com.example.CourseRegistrationSystem.dto.EnrollmentResponseDTO;
import com.example.CourseRegistrationSystem.dto.SemesterDTO;
import com.example.CourseRegistrationSystem.entity.Person;
import com.example.CourseRegistrationSystem.entity.Semester;
import com.example.CourseRegistrationSystem.entity.Student;
import com.example.CourseRegistrationSystem.exception.ResourceNotFoundException;
import com.example.CourseRegistrationSystem.repository.UserRepository;
import com.example.CourseRegistrationSystem.service.SectionService;
import com.example.CourseRegistrationSystem.service.SemesterSectionService;
import com.example.CourseRegistrationSystem.service.SemesterService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/student")
public class StudentSemesterController {

    private final UserRepository userRepository;
    private final SemesterService semesterService;
    private final SemesterSectionService semesterSectionService;
    private final SectionService sectionService;

    public StudentSemesterController(
            UserRepository userRepository,
            SemesterService semesterService,
            SemesterSectionService semesterSectionService,
            SectionService sectionService
    ) {
        this.userRepository = userRepository;
        this.semesterService = semesterService;
        this.semesterSectionService = semesterSectionService;
        this.sectionService = sectionService;
    }

    @GetMapping("/semesters")
    public ResponseEntity<List<SemesterDTO>> getSemesters(Authentication authentication) {
        Student student = getAuthenticatedStudent(authentication);

        Integer enrollmentYear = student.getEnrollmentYear();

        List<SemesterDTO> semesters = semesterService.getAllSemesters(0, 1000, "semesterId", "asc")
                .stream()
                .filter(semester -> enrollmentYear == null
                        || semester.getStartDate() == null
                        || semester.getStartDate().getYear() >= enrollmentYear)
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(semesters);
    }

    @GetMapping("/semesters/{semesterId}/sections")
    public ResponseEntity<List<Long>> getSectionsForSemester(
            Authentication authentication,
            @PathVariable Long semesterId
    ) {
        getAuthenticatedStudent(authentication);
        return ResponseEntity.ok(semesterSectionService.getSectionsForSemester(semesterId));
    }

    @GetMapping("/semesters/{semesterId}/sections/details")
    public ResponseEntity<ApiResponse<List<EnrollmentResponseDTO>>> getAvailableSectionsDetails(
            Authentication authentication,
            @PathVariable Long semesterId
    ) {
        // 1. Check karo ki kya request yahan pahunch rahi hai
        System.out.println("DEBUG: API Hit for Semester ID: " + semesterId);

        getAuthenticatedStudent(authentication);

        List<EnrollmentResponseDTO> details = sectionService.getAvailableSectionsForSemester(semesterId);

        // 2. Check karo ki kya service se kuch wapas aa raha hai
        System.out.println("DEBUG: Service returned items: " + (details != null ? details.size() : "NULL"));

        return ResponseEntity.ok(new ApiResponse<>(true, "Sections fetched", details, null));
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