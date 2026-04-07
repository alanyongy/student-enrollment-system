package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.dto.EnrollmentResponseDTO;

import java.util.List;

public interface StudentEnrollmentService {

    EnrollmentResult enrollInSection(Long studentId, Long sectionId);

    List<EnrollmentResponseDTO> getEnrollmentsForStudent(Long studentId);

    boolean dropEnrollment(Long studentId, Long sectionId);

    class EnrollmentResult {
        private boolean success;
        private EnrollmentResponseDTO enrollment;
        private List<String> errors;
        private ErrorType errorType;

        public enum ErrorType { NOT_FOUND, CONFLICT, UNPROCESSABLE, BAD_REQUEST }

        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }

        public EnrollmentResponseDTO getEnrollment() { return enrollment; }
        public void setEnrollment(EnrollmentResponseDTO enrollment) { this.enrollment = enrollment; }

        public List<String> getErrors() { return errors; }
        public void setErrors(List<String> errors) { this.errors = errors; }

        public ErrorType getErrorType() { return errorType; }
        public void setErrorType(ErrorType errorType) { this.errorType = errorType; }
    }
}
