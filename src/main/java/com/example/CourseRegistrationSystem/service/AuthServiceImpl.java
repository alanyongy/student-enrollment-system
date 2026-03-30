package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.dto.LoginRequest;
import com.example.CourseRegistrationSystem.dto.SignupRequest;
import com.example.CourseRegistrationSystem.entity.Person;
import com.example.CourseRegistrationSystem.entity.Undergrad;
import com.example.CourseRegistrationSystem.enums.SecurityRole;
import com.example.CourseRegistrationSystem.repository.UserRepository;
import com.example.CourseRegistrationSystem.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Optional;

@Service
class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentService studentService;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           StudentService studentService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.studentService = studentService;
    }

    public void signup(SignupRequest request) {
        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        // For now we create an Undergrad as the default student type
        Undergrad user = new Undergrad();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setRole(SecurityRole.STUDENT);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Ensure required Student/Undergrad fields so Bean Validation passes
        if (user.getEnrollmentYear() == null) {
            user.setEnrollmentYear(Year.now().getValue());
        }
        if (user.getAcademicStatus() == null || user.getAcademicStatus().isBlank()) {
            user.setAcademicStatus("ACTIVE");
        }
        if (user.getCreditsEarned() == null) {
            user.setCreditsEarned(0);
        }
        if (user.getYearOfStudy() == null) {
            user.setYearOfStudy(1);
        }

        // Persist via StudentService so all student logic stays centralized
        studentService.createStudent(user);
    }

    public String login(LoginRequest request) {
        Optional<Person> optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isEmpty()
                || !passwordEncoder.matches(request.getPassword(), optionalUser.get().getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        Person user = optionalUser.get();
        JwtService jwtService = new JwtService();
        String token = jwtService.generateToken(user.getEmail());
        return token;
    }
}
