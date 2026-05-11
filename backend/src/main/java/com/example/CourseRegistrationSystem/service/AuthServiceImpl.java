package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.dto.LoginRequest;
import com.example.CourseRegistrationSystem.dto.SignupRequest;
import com.example.CourseRegistrationSystem.entity.Person;
import com.example.CourseRegistrationSystem.repository.UserRepository;
import com.example.CourseRegistrationSystem.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void signup(SignupRequest request) {
        // Signup is disabled for students; accounts must be created by admin/seed.
        throw new RuntimeException("Signup is disabled. Please contact administration.");
    }

    @Override
    public String login(LoginRequest request) {
        Optional<Person> optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isEmpty()
                || !passwordEncoder.matches(request.getPassword(), optionalUser.get().getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        Person user = optionalUser.get();
        JwtService jwtService = new JwtService();
        return jwtService.generateToken(user);
    }
}
