package com.example.CourseRegistrationSystem.security;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.example.CourseRegistrationSystem.entity.Person;
import com.example.CourseRegistrationSystem.entity.Student;
import com.example.CourseRegistrationSystem.entity.Administrator;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final String SECRET_KEY = "super_long_secret_key + adding additional bytes to meet JWT SHA requirements";

    public String generateToken(Person user) {

        String role;

        if (user instanceof Student) {
            role = "ROLE_STUDENT";
        } else if (user instanceof Administrator) {
            role = "ROLE_ADMIN";
        } else {
            throw new RuntimeException("Unknown user type");
        }

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("roles", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String extractRole(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("roles", String.class);
    }
}