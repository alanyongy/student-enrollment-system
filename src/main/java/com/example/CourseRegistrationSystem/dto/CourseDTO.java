package com.example.CourseRegistrationSystem.dto;

public record CourseDTO(
    Long courseId,
    String courseNumber,
    String title,
    Integer credits     // add more if necessary
) {}
