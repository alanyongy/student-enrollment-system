package com.example.CourseRegistrationSystem.controller;

import com.example.CourseRegistrationSystem.dto.ApiResponse;
import com.example.CourseRegistrationSystem.dto.LoginRequest;
import com.example.CourseRegistrationSystem.dto.SignupRequest;
import com.example.CourseRegistrationSystem.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthControllerTest {
    
    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }    

    @Test
    void testSignupSuccess() {
        SignupRequest request = new SignupRequest();
        doNothing().when(authService).signup(request);
        ResponseEntity<ApiResponse> response = authController.signup(request);
        assertTrue(response.getBody().isSuccess());
        assertEquals("User registered successfully", response.getBody().getMessage());
    }

    @Test
    void testSignupFailure() {
        SignupRequest request = new SignupRequest();
        doThrow(new RuntimeException("Email already exists")).when(authService).signup(request);
        ResponseEntity<ApiResponse> response = authController.signup(request);
        assertFalse(response.getBody().isSuccess());
        assertEquals("Email already exists", response.getBody().getMessage());
    }    

        @Test
    void testLoginSuccess() {
        LoginRequest request = new LoginRequest();
        String token = "mock-jwt-token";
        when(authService.login(request)).thenReturn(token);
        ResponseEntity<ApiResponse> response = authController.login(request);
        assertTrue(response.getBody().isSuccess());
        assertTrue(response.getBody().getMessage().contains(token));
    }

        @Test
    void testLoginFailure() {
        LoginRequest request = new LoginRequest();
        when(authService.login(request)).thenThrow(new RuntimeException("Invalid credentials"));
        ResponseEntity<ApiResponse> response = authController.login(request);
        assertFalse(response.getBody().isSuccess());
        assertEquals("Invalid credentials", response.getBody().getMessage());
    }

}
