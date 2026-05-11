package com.example.CourseRegistrationSystem.controller;

import com.example.CourseRegistrationSystem.entity.Department;
import com.example.CourseRegistrationSystem.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class AdminDepartmentControllerTest {
    
    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private AdminDepartmentController adminDepartmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllDepartments() {
        List<Department> departments = Arrays.asList(new Department(), new Department());
        when(departmentService.getAllDepartments(0, 10, "deptId", "asc")).thenReturn(departments);
        ResponseEntity<List<Department>> response = adminDepartmentController.getAllDepartments(0, 10, "deptId", "asc");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetDepartmentById() {
        Department department = new Department();
        when(departmentService.getDepartmentById(1L)).thenReturn(department);
        ResponseEntity<Department> response = adminDepartmentController.getDepartmentById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(department, response.getBody());
    }

    @Test
    void testCreateDepartment() {
        Department department = new Department();
        when(departmentService.createDepartment(department)).thenReturn(department);
        ResponseEntity<Department> response = adminDepartmentController.createDepartment(department);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(department, response.getBody());
    }

    @Test
    void testUpdateDepartment() {
        Department department = new Department();
        when(departmentService.updateDepartment(1L, department)).thenReturn(department);
        ResponseEntity<Department> response = adminDepartmentController.updateDepartment(1L, department);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(department, response.getBody());
    }

    @Test
    void testDeleteDepartment() {
        doNothing().when(departmentService).deleteDepartment(1L);
        ResponseEntity<Void> response = adminDepartmentController.deleteDepartment(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testAssignProgramToDepartment() {
        doNothing().when(departmentService).assignProgramToDepartment(1L, 2L);
        ResponseEntity<Void> response = adminDepartmentController.assignProgramToDepartment(1L, 2L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(departmentService).assignProgramToDepartment(1L, 2L);
    }

    @Test
    void testRemoveProgramFromDepartment() {
        doNothing().when(departmentService).removeProgramFromDepartment(1L, 2L);
        ResponseEntity<Void> response = adminDepartmentController.removeProgramFromDepartment(1L, 2L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(departmentService).removeProgramFromDepartment(1L, 2L);
    }

}