package com.example.CourseRegistrationSystem.repository;

import com.example.CourseRegistrationSystem.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Person, Long> {
    
    Optional<Person> findByEmail(String email);
    
    boolean existsByEmail(String email);
}
