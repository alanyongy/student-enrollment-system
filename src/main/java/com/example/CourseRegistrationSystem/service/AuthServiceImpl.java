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

import java.util.Optional;


@Service
class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void signup(SignupRequest request) {
        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        Person user = new Undergrad();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setRole(SecurityRole.STUDENT);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
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
