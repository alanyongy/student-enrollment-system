package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.dao.CourseDAO;
import com.example.CourseRegistrationSystem.dao.ProgramCourseAccessDAO;
import com.example.CourseRegistrationSystem.dao.ProgramDAO;
import com.example.CourseRegistrationSystem.entity.Course;
import com.example.CourseRegistrationSystem.entity.Program;
import com.example.CourseRegistrationSystem.entity.ProgramCourseAccess;
import com.example.CourseRegistrationSystem.exception.ResourceNotFoundException;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ProgramCourseAccessServiceImpl implements ProgramCourseAccessService {

    private final ProgramCourseAccessDAO programCourseAccessDAO;
    private final ProgramDAO programDAO;
    private final CourseDAO courseDAO;

    public ProgramCourseAccessServiceImpl(ProgramCourseAccessDAO programCourseAccessDAO, ProgramDAO programDAO, CourseDAO courseDAO) {
        this.programCourseAccessDAO = programCourseAccessDAO;
        this.programDAO = programDAO;
        this.courseDAO = courseDAO;
    }


    @Override
    public void add(long programId, long courseId) {
        programCourseAccessDAO.addCourseToProgram(programId, courseId);
    }

    @Transactional
    @Override
    public void delete(long accessId) {
        ProgramCourseAccess entity = programCourseAccessDAO.findById(accessId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
    
        programCourseAccessDAO.removeCourseFromProgram(entity);
    }

    @Override
    public boolean isCourseInProgram(long programId, long courseId) {
        return programCourseAccessDAO.isCourseInProgram(programId, courseId);
    }

    @Transactional
    @Override
    public ProgramCourseAccess update(long id, ProgramCourseAccess req) {

        ProgramCourseAccess existing =
                programCourseAccessDAO.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Access not found"));

        // hydrate references properly (avoid detached entities)
        if (req.getProgram() != null) {
            Program program = programDAO.findById(req.getProgram().getProgramId());
            existing.setProgram(program);
        }

        if (req.getCourse() != null) {
            Course course = courseDAO.findById(req.getCourse().getCourseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
            existing.setCourse(course);
        }

        return programCourseAccessDAO.save(existing);
    }

    @Override
    @Transactional
    public List<ProgramCourseAccess> getAll(int page, int size, String sortBy, String direction) {
        return programCourseAccessDAO.findAll(page, size, sortBy, direction);
    }
}
