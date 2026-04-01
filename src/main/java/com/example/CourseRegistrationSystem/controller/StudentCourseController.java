package com.example.CourseRegistrationSystem.controller;

import com.example.CourseRegistrationSystem.dao.CourseDAO;
import com.example.CourseRegistrationSystem.dto.CourseDTO;
import com.example.CourseRegistrationSystem.entity.Course;
import com.example.CourseRegistrationSystem.entity.Person;
import com.example.CourseRegistrationSystem.entity.Student;
import com.example.CourseRegistrationSystem.exception.ResourceNotFoundException;
import com.example.CourseRegistrationSystem.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/student")
public class StudentCourseController {

    private final UserRepository userRepository;
    private final CourseDAO courseDAO;

    public StudentCourseController(UserRepository userRepository, CourseDAO courseDAO) {
        this.userRepository = userRepository;
        this.courseDAO = courseDAO;
    }

    // GET /api/student/courses?semesterId={id}
    @GetMapping("/courses")
    public ResponseEntity<List<CourseDTO>> getCourses(@RequestParam Long semesterId) {
        List<Course> courses = courseDAO.findAllBySemester(semesterId);
        List<CourseDTO> dtos = courses.stream()
                                      .map(this::mapToDto)
                                      .toList();
        return ResponseEntity.ok(dtos);
    }
    
    @GetMapping("/courses/{courseId}")
    public ResponseEntity<CourseDTO> getCourse(
            @PathVariable Long courseId,
            @RequestParam Long semesterId
    ) {
        Course course = courseDAO.findByIdAndSemester(courseId, semesterId)
                                 .orElseThrow(() -> new ResourceNotFoundException(
                                     "Course not found in semester: " + semesterId));
        return ResponseEntity.ok(mapToDto(course));
    }

    // GET /api/student/courses/{courseId}/eligibility?semesterId={id} (optional)
    @GetMapping("/courses/{courseId}/eligibility")
    public ResponseEntity<Boolean> checkCourseEligibility(
            Authentication authentication,
            @PathVariable("courseId") Long courseId,
            @RequestParam("semesterId") Long semesterId
    ) {
        Student student = getAuthenticatedStudent(authentication);

        //TODO: replace with actual eligibility logic
        boolean eligible = true;

        return ResponseEntity.ok(eligible);
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

    private CourseDTO mapToDto(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setCourseId(course.getCourseId());
        dto.setCourseNumber(course.getCourseNumber());
        dto.setDescription(course.getDescription());
        dto.setCredits(course.getCredits());
        dto.setDepartmentName(course.getDepartment() != null ? course.getDepartment().getDeptName() : null);
        return dto;
    }
}