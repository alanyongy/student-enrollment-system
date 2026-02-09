package com.example.CourseRegistrationSystem.controller;

import com.example.CourseRegistrationSystem.entity.User;
import com.example.CourseRegistrationSystem.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    //private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        //this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        // if (userRepository.existsByEmail(user.getEmail())) {
        //     return "Email already has an account";
        // }

        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public String login(@RequestBody User loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElse(null);
        if (user == null /*||!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())*/) {
            return "Invalid username or password";
        }
        return "Login successful";
    }
}
