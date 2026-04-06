package com.example.CourseRegistrationSystem.controller;

import com.example.CourseRegistrationSystem.entity.Section;
import com.example.CourseRegistrationSystem.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/sections")
public class AdminSectionController {

    @Autowired
    private SectionService sectionService;

    @GetMapping
    public ResponseEntity<List<Section>> getAllSections(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "sectionId") String sortBy,
        @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(sectionService.getAllSections(page, size, sortBy, direction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Section> getSectionById(@PathVariable Long id) {
        return ResponseEntity.ok(sectionService.getSectionById(id));
    }

    @PostMapping
    public ResponseEntity<Section> createSection(@RequestBody Section section) {
        return ResponseEntity.ok(sectionService.createSection(section));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Section> updateSection(@PathVariable Long id, @RequestBody Section section) {
        return ResponseEntity.ok(sectionService.updateSection(id, section));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSection(@PathVariable Long id) {
        sectionService.deleteSection(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{sectionId}/assign-instructor/{instructorId}")
    public ResponseEntity<Void> assignInstructorToSection(@PathVariable Long sectionId, @PathVariable Long instructorId) {
        sectionService.assignInstructor(sectionId, instructorId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{sectionId}/remove-instructor")
    public ResponseEntity<Void> removeInstructorFromSection(@PathVariable Long sectionId) {
        sectionService.removeInstructor(sectionId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{sectionId}/courses/{courseId}")
    public ResponseEntity<Void> assignCourseToSection(@PathVariable Long sectionId, @PathVariable Long courseId) {
        sectionService.assignCourse(sectionId, courseId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{sectionId}/courses/{courseId}")
    public ResponseEntity<?> removeCourseToSection(@PathVariable Long sectionId, @PathVariable Long courseId) {
        sectionService.removeCourse(sectionId, courseId);
        return ResponseEntity.ok().build();
    }
}
