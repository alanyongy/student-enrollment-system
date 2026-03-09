package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.entity.Section;
import java.util.List;

public interface SectionService {
    List<Section> getAllSections();
    Section getSectionById(Long id);
    Section createSection(Section section);
    Section updateSection(Long id, Section section);
    void deleteSection(Long id);
}

