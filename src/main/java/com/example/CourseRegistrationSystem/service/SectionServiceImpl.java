package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.dao.SectionDAO;
import com.example.CourseRegistrationSystem.entity.Course;
import com.example.CourseRegistrationSystem.entity.Section;
import com.example.CourseRegistrationSystem.entity.Semester;
import com.example.CourseRegistrationSystem.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionServiceImpl implements SectionService {

    @Autowired
    private SectionDAO sectionDAO;

    @Autowired
    private CourseService courseService;
    private SemesterService semesterService;

    public SectionServiceImpl(CourseService courseService, SemesterService semesterService){
        this.courseService = courseService;
        this.semesterService = semesterService;
    }

    @Override
    public List<Section> getAllSections(int page, int size, String sortBy, String direction) {
        return sectionDAO.findAll(page, size, sortBy, direction);
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
    public Section updateSection(Long id, Section req) {

        Section existing = sectionDAO.findById(id);
        Course course = courseService.getCourse(req.getCourse().getCourseId());
        Semester semester = semesterService.getSemesterById(req.getSemester().getSemesterId());

        existing.setCourse(course);
        existing.setSemester(semester);
        existing.setScheduleTime(req.getScheduleTime());
        existing.setLocation(req.getLocation());
        existing.setCapacity(req.getCapacity());

        return sectionDAO.save(existing);
    }

    @Override
    public void deleteSection(Long id) {
        sectionDAO.delete(id);
    }

    @Override
    public void assignInstructor(Long sectionId, Long instructorId) {
        sectionDAO.assignInstructor(sectionId, instructorId);
    }

    @Override
    public void removeInstructor(Long sectionId) {
        sectionDAO.removeInstructor(sectionId);
    }

    @Override
    public void assignCourse(Long sectionId, Long courseId) {
        Section section = getSectionById(sectionId);
        Course course = courseService.getCourse(courseId);
        section.setCourse(course);
        sectionDAO.update(section);
    }

    @Override
    public void removeCourse(Long sectionId, Long courseId) {
        Section section = getSectionById(sectionId);
        Course course = courseService.getCourse(courseId);
        section.setCourse(null);
        sectionDAO.update(section);
    }
}
