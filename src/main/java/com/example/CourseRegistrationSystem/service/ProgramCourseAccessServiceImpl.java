package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.dao.ProgramCourseAccessDAO;
import org.springframework.stereotype.Service;

@Service
public class ProgramCourseAccessServiceImpl implements ProgramCourseAccessService {

    private final ProgramCourseAccessDAO programCourseAccessDAO;

    public ProgramCourseAccessServiceImpl(ProgramCourseAccessDAO programCourseAccessDAO) {
        this.programCourseAccessDAO = programCourseAccessDAO;
    }


    @Override
    public void addCourseToProgram(long programId, long courseId) {
        programCourseAccessDAO
                .addCourseToProgram(programId, courseId);
    }

    @Override
    public void removeCourseFromProgram(long programId, long courseId) {
        programCourseAccessDAO.removeCourseFromProgram(programId, courseId);
    }

    @Override
    public boolean isCourseInProgram(long programId, long courseId) {
        return programCourseAccessDAO.isCourseInProgram(programId, courseId);
    }
}
