package com.example.CourseRegistrationSystem.controller;

import com.example.CourseRegistrationSystem.entity.Section;
import com.example.CourseRegistrationSystem.service.SectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/student/sections")
public class StudentSectionController {

    private final SectionService sectionService;

    public StudentSectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Section> getSectionById(@PathVariable Long id) {
        return ResponseEntity.ok(sectionService.getSectionById(id));
    }

    @GetMapping("/{sectionId}/courses")
    public ResponseEntity<List<Long>> getCourseForSection(@PathVariable Long sectionId) {
        return ResponseEntity.ok(sectionService.getCoursesForSection(sectionId));
    }
}
