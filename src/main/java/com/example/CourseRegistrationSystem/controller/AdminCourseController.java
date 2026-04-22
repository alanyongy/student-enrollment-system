package com.example.CourseRegistrationSystem.controller;

import com.example.CourseRegistrationSystem.entity.Course;
import com.example.CourseRegistrationSystem.entity.CoursePrerequisite;
import com.example.CourseRegistrationSystem.service.CoursePrerequisiteService;
import com.example.CourseRegistrationSystem.service.CourseService;
import com.example.CourseRegistrationSystem.service.ProgramCourseAccessService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/courses")
public class AdminCourseController {

    private final CourseService courseService;
    private final CoursePrerequisiteService coursePrerequisiteService;
    private final ProgramCourseAccessService programCourseAccessService;

    public AdminCourseController(CourseService courseService, CoursePrerequisiteService coursePrerequisiteService, ProgramCourseAccessService programCourseAccessService) {
        this.courseService = courseService;
        this.coursePrerequisiteService = coursePrerequisiteService;
        this.programCourseAccessService = programCourseAccessService;
    }

    @GetMapping
    public ResponseEntity<List<Course>> getCourses(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(defaultValue = "courseId") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
        ) {

        return ResponseEntity.ok(courseService.getCourses(page, size, sortBy, direction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable Long id) {

        return ResponseEntity.ok(courseService.getCourse(id));
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {

        Course savedCourse = courseService.createCourse(course);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedCourse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable Long id,
            @RequestBody Course course) {

        return ResponseEntity.ok(courseService.updateCourse(id, course));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {

        courseService.deleteCourse(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/course-prerequisites")
    public ResponseEntity<List<CoursePrerequisite>> getAll() {
        return ResponseEntity.ok(coursePrerequisiteService.getAll());
    }
    
    @PostMapping("/course-prerequisites")
    public ResponseEntity<?> create(@RequestBody CoursePrerequisite req) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(coursePrerequisiteService.create(req));
    }

    @PutMapping("/course-prerequisites/{prereqId}")
    public ResponseEntity<?> update(
            @PathVariable Long prereqId,
            @RequestBody CoursePrerequisite req
    ) {
        return ResponseEntity.ok(
            coursePrerequisiteService.update(prereqId, req)
        );
    }

    @DeleteMapping("/course-prerequisites/{prereqId}")
    public ResponseEntity<?> delete(@PathVariable Long prereqId) {
        coursePrerequisiteService.delete(prereqId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{courseId}/assign-department/{deptId}")
    public ResponseEntity<Void> assignDepartmentToCourse(@PathVariable Long courseId, @PathVariable Long deptId) {
        courseService.assignDepartment(courseId, deptId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{courseId}/give-access-to-program/{programId}")
    public ResponseEntity<Void> giveCourseAccessToProgram(@PathVariable Long courseId, @PathVariable Long programId) {
        programCourseAccessService.addCourseToProgram(courseId, programId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{courseId}/remove-access-from-program/{programId}")
    public ResponseEntity<Void> removeCourseAccessFromProgram(@PathVariable Long courseId, @PathVariable Long programId) {
        programCourseAccessService.removeCourseFromProgram(courseId, programId);
        return ResponseEntity.ok().build();
    }



}
