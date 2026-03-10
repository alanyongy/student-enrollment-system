package com.example.CourseRegistrationSystem.service;

public interface ProgramCourseAccessService {
    void addCourseToProgram(long programId, long courseId);
    void removeCourseFromProgram(long programId, long courseId);
    boolean isCourseInProgram(long programId, long courseId);
}
