package com.example.CourseRegistrationSystem.dao;

import java.util.List;
import java.util.Optional;

import com.example.CourseRegistrationSystem.entity.ProgramCourseAccess;

public interface ProgramCourseAccessDAO {
    void addCourseToProgram(long programId, long courseId);
    void removeCourseFromProgram(ProgramCourseAccess entity);
    boolean isCourseInProgram(long programId, long courseId);
    Optional<ProgramCourseAccess> findById(Long id);
    ProgramCourseAccess save(ProgramCourseAccess entity);
    List<ProgramCourseAccess> findAll(int page, int size, String sortBy, String direction);
}
