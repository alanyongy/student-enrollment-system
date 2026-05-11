package com.example.CourseRegistrationSystem.controller;

import com.example.CourseRegistrationSystem.dto.ApiResponse;
import com.example.CourseRegistrationSystem.dto.EnrollmentRequestDTO;
import com.example.CourseRegistrationSystem.dto.EnrollmentResponseDTO;
import com.example.CourseRegistrationSystem.entity.Person;
import com.example.CourseRegistrationSystem.entity.Student;
import com.example.CourseRegistrationSystem.exception.ResourceNotFoundException;
import com.example.CourseRegistrationSystem.repository.UserRepository;
import com.example.CourseRegistrationSystem.service.StudentEnrollmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/enrollments")
public class StudentEnrollmentController {

    private final UserRepository userRepository;
    private final StudentEnrollmentService studentEnrollmentService;

    public StudentEnrollmentController(UserRepository userRepository,
                                       StudentEnrollmentService studentEnrollmentService) {
        this.userRepository = userRepository;
        this.studentEnrollmentService = studentEnrollmentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EnrollmentResponseDTO>> enrollInSection(
            Authentication authentication,
            @Valid @RequestBody EnrollmentRequestDTO request
    ) {
        Student student = getAuthenticatedStudent(authentication);

        StudentEnrollmentService.EnrollmentResult result =
                studentEnrollmentService.enrollInSection(student.getPersonId(), request.getSectionId());

        if (result.isSuccess()) {
            ApiResponse<EnrollmentResponseDTO> response = new ApiResponse<>(
                    true,
                    "Enrolled successfully",
                    result.getEnrollment(),
                    null
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            List<String> errors = result.getErrors();
            String message = "Enrollment failed";
            HttpStatus status = HttpStatus.BAD_REQUEST;
            if (result.getErrorType() != null) {
                switch (result.getErrorType()) {
                    case NOT_FOUND -> status = HttpStatus.NOT_FOUND;
                    case CONFLICT -> status = HttpStatus.CONFLICT;
                    case UNPROCESSABLE -> status = HttpStatus.UNPROCESSABLE_ENTITY;
                    case BAD_REQUEST -> status = HttpStatus.BAD_REQUEST;
                }
            }
            ApiResponse<EnrollmentResponseDTO> response = new ApiResponse<>(
                    false,
                    message,
                    null,
                    errors
            );
            return ResponseEntity.status(status).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EnrollmentResponseDTO>>> getEnrollments(Authentication authentication) {
        Student student = getAuthenticatedStudent(authentication);
        List<EnrollmentResponseDTO> enrollments = studentEnrollmentService.getEnrollmentsForStudent(student.getPersonId());

        ApiResponse<List<EnrollmentResponseDTO>> response = new ApiResponse<>(
                true,
                "Enrollments fetched successfully",
                enrollments,
                null
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{sectionId}")
    public ResponseEntity<ApiResponse<Void>> dropEnrollment(
            Authentication authentication,
            @PathVariable Long sectionId
    ) {
        Student student = getAuthenticatedStudent(authentication);

        boolean dropped = studentEnrollmentService.dropEnrollment(student.getPersonId(), sectionId);

        if (dropped) {
            ApiResponse<Void> response = new ApiResponse<>(
                    true,
                    "Enrollment dropped successfully",
                    null,
                    null
            );
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<Void> response = new ApiResponse<>(
                    false,
                    "Enrollment not found",
                    null,
                    List.of("Not enrolled in this section")
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
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
}
