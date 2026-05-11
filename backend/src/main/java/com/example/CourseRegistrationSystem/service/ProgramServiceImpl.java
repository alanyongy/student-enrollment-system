package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.dao.ProgramDAO;
import com.example.CourseRegistrationSystem.entity.Department;
import com.example.CourseRegistrationSystem.entity.Program;
import com.example.CourseRegistrationSystem.exception.ResourceNotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramServiceImpl implements ProgramService {

    @Autowired
    private ProgramDAO programDAO;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Program> getAllPrograms(int page, int size, String sortBy, String direction) {
    
        List<Program> programs = programDAO.findAll(page, size, sortBy, direction);
        
        programs.forEach(p -> {
            if (p.getDepartment() != null) {
                p.setDepartment(safeDepartment(p.getDepartment()));
            }
        });
    
        return programs;
    }

    private Department safeDepartment(Department d) {
        Department safe = new Department();

        safe.setDeptId(d.getDeptId());
        safe.setDeptName(d.getDeptName());
        safe.setDeptEmail(d.getDeptEmail());
        safe.setPhoneNumber(d.getPhoneNumber());
        safe.setOfficeLocation(d.getOfficeLocation());

        //prevent recursion loop back to programs
        safe.setPrograms(null);

        return safe;
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

    @Transactional
    public void deleteProgram(Long id) {
        Program program = entityManager.find(Program.class, id);
    
        if (program == null) return;
    
        entityManager.remove(program);
    }
}


