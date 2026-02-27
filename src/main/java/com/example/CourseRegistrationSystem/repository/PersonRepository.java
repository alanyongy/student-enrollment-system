package com.example.CourseRegistrationSystem.repository;

import com.example.CourseRegistrationSystem.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface PersonRepository extends JpaRepository<Person, Long> {
    
}