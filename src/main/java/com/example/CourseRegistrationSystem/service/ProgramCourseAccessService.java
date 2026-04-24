package com.example.CourseRegistrationSystem.service;

import java.util.List;

import com.example.CourseRegistrationSystem.entity.ProgramCourseAccess;

public interface ProgramCourseAccessService {
    void add(long programId, long courseId);
    void delete(long accessId);
    boolean isCourseInProgram(long programId, long courseId);
    ProgramCourseAccess update(long id, ProgramCourseAccess req);
    List<ProgramCourseAccess> getAll(int page, int size, String sortBy, String direction);
}
