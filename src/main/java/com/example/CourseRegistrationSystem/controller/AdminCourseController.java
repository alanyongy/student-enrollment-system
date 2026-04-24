package com.example.CourseRegistrationSystem.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.CourseRegistrationSystem.entity.Course;
import com.example.CourseRegistrationSystem.entity.CoursePrerequisite;
import com.example.CourseRegistrationSystem.entity.ProgramCourseAccess;
import com.example.CourseRegistrationSystem.service.CoursePrerequisiteService;
import com.example.CourseRegistrationSystem.service.CourseService;
import com.example.CourseRegistrationSystem.service.ProgramCourseAccessService;

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
    public ResponseEntity<List<CoursePrerequisite>> getAll(        
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "prerequisiteId") String sortBy,
        @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(coursePrerequisiteService.getAll(page, size, sortBy, direction));
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

    @PostMapping("/access")
    public ResponseEntity<Void> giveCourseAccessToProgram(@RequestBody ProgramCourseAccess req) {
        System.out.println("hit access get endpoint");
        Long courseId = req.getCourse().getCourseId();
        Long programId = req.getProgram().getProgramId();

        programCourseAccessService.add(programId, courseId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/access/{accessId}")
    public ResponseEntity<Void> removeCourseAccess(@PathVariable Long accessId) {
        programCourseAccessService.delete(accessId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/access")
    public ResponseEntity<List<ProgramCourseAccess>> getAllAccess(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "accessId") String sortBy,
        @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(programCourseAccessService.getAll(page, size, sortBy, direction) );
    }

    @PutMapping("/access/{id}")
    public ResponseEntity<ProgramCourseAccess> updateAccess(
            @PathVariable Long id,
            @RequestBody ProgramCourseAccess req
    ) {
        return ResponseEntity.ok(programCourseAccessService.update(id, req));
    }
}
