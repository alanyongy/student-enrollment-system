package com.example.CourseRegistrationSystem.controller;

import com.example.CourseRegistrationSystem.entity.Semester;
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

    @GetMapping
    public ResponseEntity<List<Semester>> getAllSemesters() {
        return ResponseEntity.ok(semesterService.getAllSemesters());
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
}

