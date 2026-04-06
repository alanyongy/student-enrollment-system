package com.example.CourseRegistrationSystem.controller;

import com.example.CourseRegistrationSystem.entity.Instructor;
import com.example.CourseRegistrationSystem.service.InstructorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/instructors")
public class AdminInstructorController {

    private final InstructorService instructorService;

    public AdminInstructorController(InstructorService instructorService){
        this.instructorService = instructorService;
    }

    @GetMapping
    public ResponseEntity<List<Instructor>> getInstructors(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "personId") String sortBy,
        @RequestParam(defaultValue = "asc") String direction
    ) {

        return ResponseEntity.ok(instructorService.getInstructors(page, size, sortBy, direction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instructor> getInstructor(@PathVariable Long id) {

        return ResponseEntity.ok(instructorService.getInstructor(id));
    }

    @PostMapping
    public ResponseEntity<Instructor> createInstructor(@RequestBody Instructor instructor) {

        Instructor savedInstructor = instructorService.createInstructor(instructor);

        return ResponseEntity.status(HttpStatus.CREATED).body(instructor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Instructor> updateInstructor(
            @PathVariable Long id,
            @RequestBody Instructor instructor) {

        return ResponseEntity.ok(instructorService.updateInstructor(id, instructor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable Long id) {

        instructorService.deleteInstructor(id);

        return ResponseEntity.noContent().build();
    }
}
