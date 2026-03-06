package com.example.CourseRegistrationSystem.repository;

import com.example.CourseRegistrationSystem.dto.SignupRequest;
import com.example.CourseRegistrationSystem.service.AuthService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestUserLoader {

    @Bean
    CommandLineRunner init(AuthService authService, UserRepository userRepository) {
        return args -> {

            if (!userRepository.existsByEmail("student@example.com")) {
                SignupRequest student = new SignupRequest();
                student.setFirstName("Test");
                student.setLastName("Student");
                student.setEmail("student@example.com");
                student.setPassword("student123");

                authService.signup(student);
            }

            if (!userRepository.existsByEmail("admin@example.com")) {
                SignupRequest admin = new SignupRequest();
                admin.setFirstName("Test");
                admin.setLastName("Admin");
                admin.setEmail("admin@example.com");
                admin.setPassword("admin123");

                authService.signup(admin);
            }
        };
    }
}