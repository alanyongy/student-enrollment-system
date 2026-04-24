package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.dao.CourseDAO;
import com.example.CourseRegistrationSystem.dao.CoursePrerequisiteDAO;
import com.example.CourseRegistrationSystem.entity.Course;
import com.example.CourseRegistrationSystem.entity.CoursePrerequisite;
import com.example.CourseRegistrationSystem.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CoursePrerequisiteServiceImpl implements CoursePrerequisiteService {

    private final CourseDAO courseDAO;
    private final CoursePrerequisiteDAO coursePrerequisiteDAO;

    @Autowired
    public CoursePrerequisiteServiceImpl(CourseDAO courseDAO, CoursePrerequisiteDAO coursePrerequisiteDAO) {
        this.courseDAO = courseDAO;
        this.coursePrerequisiteDAO = coursePrerequisiteDAO;
    }

    @Transactional
    @Override
    public void addPrerequisite(Long courseId, Long prerequisiteCourseId) {
        Course course = courseDAO.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + courseId));
        Course prerequisiteCourse = courseDAO.findById(prerequisiteCourseId)
                .orElseThrow(() -> new ResourceNotFoundException("Prerequisite course not found with id " + prerequisiteCourseId));
        coursePrerequisiteDAO.addPrerequisite(course, prerequisiteCourse);
    }

    @Transactional
    @Override
    public void removePrerequisite(Long courseId, Long prerequisiteCourseId) {
        Course course = courseDAO.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + courseId));
        Course prerequisiteCourse = courseDAO.findById(prerequisiteCourseId)
                .orElseThrow(() -> new ResourceNotFoundException("Prerequisite course not found with id " + prerequisiteCourseId));
        coursePrerequisiteDAO.removePrerequisite(course, prerequisiteCourse);
    }

    @Transactional
    @Override
    public List<CoursePrerequisite> getPrerequisitesForCourse(Long courseId) {
            Course course = courseDAO.findById(courseId)
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + courseId));

            List<CoursePrerequisite> prerequisites = coursePrerequisiteDAO.getPrerequisites(course);

            if (prerequisites.isEmpty()) {
                throw new ResourceNotFoundException("No prerequisites found for course with id " + courseId);
            }
            return prerequisites;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CoursePrerequisite> getAll(int page, int size, String sortBy, String direction)  {
    
        List<CoursePrerequisite> list = coursePrerequisiteDAO.findAll(page, size, sortBy, direction) ;
    
        list.forEach(cp -> {
            cp.setCourse(safeCourse(cp.getCourse()));
            cp.setPrerequisiteCourse(safeCourse(cp.getPrerequisiteCourse()));
        });
    
        return list;
    }
    
    private Course safeCourse(Course c) {
        if (c == null) return null;
    
        Course safe = new Course();
        safe.setCourseId(c.getCourseId());
        safe.setCourseNumber(c.getCourseNumber());
        safe.setDescription(c.getDescription());
        safe.setCredits(c.getCredits());
    
        return safe;
    }
    
    @Transactional
    @Override
    public CoursePrerequisite create(CoursePrerequisite req) {
    
        Long courseId = req.getCourse().getCourseId();
        Long prereqId = req.getPrerequisiteCourse().getCourseId();
    
        Course course = courseDAO.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + courseId));
    
        Course prerequisiteCourse = courseDAO.findById(prereqId)
                .orElseThrow(() -> new ResourceNotFoundException("Prerequisite course not found with id " + prereqId));
    
        CoursePrerequisite cp = new CoursePrerequisite();
        cp.setCourse(course);
        cp.setPrerequisiteCourse(prerequisiteCourse);
    
        return coursePrerequisiteDAO.save(cp);
    }
    
    @Transactional
    @Override
    public CoursePrerequisite update(Long id, CoursePrerequisite req) {
    
        CoursePrerequisite existing = coursePrerequisiteDAO.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Prerequisite not found"));
    
        Long courseId = req.getCourse().getCourseId();
        Long prereqId = req.getPrerequisiteCourse().getCourseId();
    
        Course course = courseDAO.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + courseId));
    
        Course prerequisiteCourse = courseDAO.findById(prereqId)
                .orElseThrow(() -> new ResourceNotFoundException("Prerequisite course not found with id " + prereqId));
    
        existing.setCourse(course);
        existing.setPrerequisiteCourse(prerequisiteCourse);
    
        return coursePrerequisiteDAO.save(existing);
    }
    
    @Transactional
    @Override
    public void delete(Long id) {
        CoursePrerequisite cp = coursePrerequisiteDAO.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Not found"));
    
        coursePrerequisiteDAO.delete(cp);
    }
}

