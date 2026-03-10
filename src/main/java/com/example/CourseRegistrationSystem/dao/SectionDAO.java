package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Section;
import java.util.List;

public interface SectionDAO {
    List<Section> findAll();
    Section findById(Long id);
    Section save(Section section);
    Section update(Section section);
    void delete(Long id);
    void assignInstructor(Long sectionId, Long instructorId);
    void removeInstructor(Long sectionId);
}
