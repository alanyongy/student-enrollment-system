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
    public ResponseEntity<List<Section>> getAllSections() {
        return ResponseEntity.ok(sectionService.getAllSections());
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
}

