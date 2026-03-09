package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.dao.DepartmentDAO;
import com.example.CourseRegistrationSystem.entity.Department;
import com.example.CourseRegistrationSystem.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentDAO departmentDAO;

    public DepartmentServiceImpl(DepartmentDAO departmentDAO) {
        this.departmentDAO = departmentDAO;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Department> getAllDepartments() {
        return departmentDAO.findAll();
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
    @Override
    public void deleteDepartment(Long id) {
        Department department = departmentDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
        departmentDAO.delete(department);
    }
}
