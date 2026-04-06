package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.dao.ProgramDAO;
import com.example.CourseRegistrationSystem.entity.Program;
import com.example.CourseRegistrationSystem.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramServiceImpl implements ProgramService {

    @Autowired
    private ProgramDAO programDAO;

    @Override
    public List<Program> getAllPrograms(int page, int size, String sortBy, String direction) {
        return programDAO.findAll(page, size, sortBy, direction);
    }

    @Override
    public Program getProgramById(Long id) {
        Program program = programDAO.findById(id);
        if (program == null) {
            throw new ResourceNotFoundException("Program not found with id: " + id);
        }
        return program;
    }

    @Override
    public Program createProgram(Program program) {
        return programDAO.save(program);
    }

    @Override
    public Program updateProgram(Long id, Program program) {
        Program existing = getProgramById(id);
        existing.setProgramName(program.getProgramName());
        existing.setDescription(program.getDescription());
        existing.setDepartment(program.getDepartment());
        existing.setProgramType(program.getProgramType());
        return programDAO.update(existing);
    }

    @Override
    public void deleteProgram(Long id) {
        programDAO.delete(id);
    }
}


