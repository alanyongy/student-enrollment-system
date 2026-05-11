package com.example.CourseRegistrationSystem.dto;

import jakarta.validation.constraints.NotNull;

public class EnrollmentRequestDTO {

    @NotNull
    private Long sectionId;

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }
}

