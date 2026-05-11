package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.dao.CourseDAO;
import com.example.CourseRegistrationSystem.dao.DepartmentDAO;
import com.example.CourseRegistrationSystem.dao.ProgramDAO;
import com.example.CourseRegistrationSystem.entity.Course;
import com.example.CourseRegistrationSystem.entity.Department;
import com.example.CourseRegistrationSystem.entity.Program;
import com.example.CourseRegistrationSystem.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentDAO departmentDAO;
    private final ProgramService programService;
    private final CourseDAO courseDAO;

    public DepartmentServiceImpl(DepartmentDAO departmentDAO, ProgramService programService, CourseDAO courseDAO) {
        this.departmentDAO = departmentDAO;
        this.programService = programService;
        this.courseDAO = courseDAO;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Department> getAllDepartments(int page, int size, String sortBy, String direction) {
        return departmentDAO.findAll(page, size, sortBy, direction);
    }

    @Transactional(readOnly = true)
    @Override
    public Department getDepartmentById(Long id) {
        return departmentDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
    }

    @Transactional
    @Override
    public Department createDepartment(Department department) {
        department.setDeptId(null);
        return departmentDAO.save(department);
    }

    @Transactional
    @Override
    public Department updateDepartment(Long id, Department department) {
        Department existing = departmentDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
        existing.setDeptName(department.getDeptName());
        existing.setDeptEmail(department.getDeptEmail());
        existing.setPhoneNumber(department.getPhoneNumber());
        existing.setOfficeLocation(department.getOfficeLocation());
        return departmentDAO.save(existing);
    }

    @Transactional
    public void deleteDepartment(Long deptId) {

        Department dept = departmentDAO.findById(deptId)
            .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        List<Course> courses = courseDAO.findByDepartment(deptId);
        courses.forEach(c -> {
            c.setDepartment(null);
            courseDAO.save(c);
        });

        departmentDAO.delete(dept);
    }

    @Override
    public void assignProgramToDepartment(Long departmentId, Long programId) {
        Program program = programService.getProgramById(programId);
        Department department = departmentDAO.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + departmentId));
        program.setDepartment(department);
        programService.updateProgram(programId, program);
    }

    @Override
    public void removeProgramFromDepartment(Long departmentId, Long programId) {
        Program program = programService.getProgramById(programId);
        if (program.getDepartment() != null && program.getDepartment().getDeptId().equals(departmentId)) {
            program.setDepartment(null);
            programService.updateProgram(programId, program);
        } else {
            throw new ResourceNotFoundException("Program with id " + programId + " is not assigned to department with id " + departmentId);
        }
    }
}
