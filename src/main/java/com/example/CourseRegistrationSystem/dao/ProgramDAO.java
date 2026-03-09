package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Program;
import java.util.List;

public interface ProgramDAO {
    List<Program> findAll();
    Program findById(Long id);
    Program save(Program program);
    Program update(Program program);
    void delete(Long id);
}

