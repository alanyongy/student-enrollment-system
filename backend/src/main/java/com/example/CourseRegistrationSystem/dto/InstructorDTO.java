package com.example.CourseRegistrationSystem.dto;

public record InstructorDTO(
    Long personId,
    String firstName,
    String lastName,
    Integer yearsOfExperience,
    String expertiseTopics
) {}
