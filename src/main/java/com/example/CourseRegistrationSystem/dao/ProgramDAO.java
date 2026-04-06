package com.example.CourseRegistrationSystem.dao;

import com.example.CourseRegistrationSystem.entity.Program;
import java.util.List;

public interface ProgramDAO {
    List<Program> findAll(int page, int size, String sortBy, String direction);
    Program findById(Long id);
    Program save(Program program);
    Program update(Program program);
    void delete(Long id);
}

