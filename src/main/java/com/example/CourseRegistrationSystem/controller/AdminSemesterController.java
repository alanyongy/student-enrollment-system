package com.example.CourseRegistrationSystem.controller;

import com.example.CourseRegistrationSystem.entity.Semester;
import com.example.CourseRegistrationSystem.service.SemesterSectionService;
import com.example.CourseRegistrationSystem.service.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/semesters")
public class AdminSemesterController {

    @Autowired
    private SemesterService semesterService;
    @Autowired
    private SemesterSectionService semesterSectionService;

    @GetMapping
    public ResponseEntity<List<Semester>> getAllSemesters(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "semesterId") String sortBy,
        @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(semesterService.getAllSemesters(page, size, sortBy, direction));
    }

    @PostMapping
    public ResponseEntity<Semester> createSemester(@RequestBody Semester semester) {
        return ResponseEntity.ok(semesterService.createSemester(semester));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Semester> updateSemester(@PathVariable Long id, @RequestBody Semester semester) {
        return ResponseEntity.ok(semesterService.updateSemester(id, semester));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSemester(@PathVariable Long id) {
        semesterService.deleteSemester(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{semesterId}/sections/{sectionId}")
    public ResponseEntity<Void> addSectionToSemester(@PathVariable Long semesterId, @PathVariable Long sectionId) {
        semesterSectionService.addSemesterSection(semesterId, sectionId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{semesterId}/sections/{sectionId}")
    public ResponseEntity<Void> removeSectionFromSemester(@PathVariable Long semesterId, @PathVariable Long sectionId) {
        semesterSectionService.removeSemesterSection(semesterId, sectionId);
        return ResponseEntity.noContent().build();
    }
}

