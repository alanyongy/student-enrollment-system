package com.example.CourseRegistrationSystem.dao;

public interface ProgramCourseAccessDAO {
    void addCourseToProgram(long programId, long courseId);
    void removeCourseFromProgram(long programId, long courseId);
    boolean isCourseInProgram(long programId, long courseId);
}
