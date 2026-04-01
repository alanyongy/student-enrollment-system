package com.example.CourseRegistrationSystem.repository;

import com.example.CourseRegistrationSystem.dto.SignupRequest;
import com.example.CourseRegistrationSystem.entity.Administrator;
import com.example.CourseRegistrationSystem.entity.Person;
import com.example.CourseRegistrationSystem.entity.Student;
import com.example.CourseRegistrationSystem.entity.Undergrad;
import com.example.CourseRegistrationSystem.enums.SecurityRole;
import com.example.CourseRegistrationSystem.service.AuthService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class TestUserLoader {

    @Bean
    CommandLineRunner init(AuthService authService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            if (!userRepository.existsByEmail("student@example.com")) {
                Undergrad student = new Undergrad();
                student.setFirstName("Test");
                student.setLastName("Student");
                student.setEmail("student@example.com");
                student.setPassword(passwordEncoder.encode("student123"));

                student.setCreditsEarned(1);
                student.setAcademicStatus("test");
                student.setYearOfStudy(1);
                student.setEnrollmentYear(2026);
                student.setRole(SecurityRole.STUDENT);

                userRepository.save(student);
            }

            if (!userRepository.existsByEmail("admin@example.com")) {
                Person admin = new Administrator();
                admin.setFirstName("Test");
                admin.setLastName("Admin");
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(SecurityRole.ADMIN);

                userRepository.save(admin);
            }
        };
    }
}