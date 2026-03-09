package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.dao.SectionDAO;
import com.example.CourseRegistrationSystem.entity.Section;
import com.example.CourseRegistrationSystem.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionServiceImpl implements SectionService {

    @Autowired
    private SectionDAO sectionDAO;

    @Override
    public List<Section> getAllSections() {
        return sectionDAO.findAll();
    }

    @Override
    public Section getSectionById(Long id) {
        Section section = sectionDAO.findById(id);
        if (section == null) {
            throw new ResourceNotFoundException("Section not found with id: " + id);
        }
        return section;
    }

    @Override
    public Section createSection(Section section) {
        return sectionDAO.save(section);
    }

    @Override
    public Section updateSection(Long id, Section section) {
        Section existing = getSectionById(id);
        existing.setScheduleTime(section.getScheduleTime());
        existing.setLocation(section.getLocation());
        existing.setCapacity(section.getCapacity());
        existing.setCourse(section.getCourse());
        existing.setInstructor(section.getInstructor());
        return sectionDAO.update(existing);
    }

    @Override
    public void deleteSection(Long id) {
        sectionDAO.delete(id);
    }
}

