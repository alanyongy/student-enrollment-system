package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.dto.LoginRequest;
import com.example.CourseRegistrationSystem.dto.SignupRequest;

public interface AuthService {
    void signup(SignupRequest request);
    String login(LoginRequest request);
}