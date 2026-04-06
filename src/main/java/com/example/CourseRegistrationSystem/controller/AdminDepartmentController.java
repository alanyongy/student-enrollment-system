package com.example.CourseRegistrationSystem.controller;

import com.example.CourseRegistrationSystem.entity.Department;
import com.example.CourseRegistrationSystem.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/departments")
public class AdminDepartmentController {

    private final DepartmentService departmentService;

    public AdminDepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "deptId") String sortBy,
        @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(departmentService.getAllDepartments(page, size, sortBy, direction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department saved = departmentService.createDepartment(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        return ResponseEntity.ok(departmentService.updateDepartment(id, department));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{departmentId}/programs/{programId}")
    public ResponseEntity<Void> assignProgramToDepartment(
            @PathVariable Long departmentId,
            @PathVariable Long programId) {
        departmentService.assignProgramToDepartment(departmentId, programId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{departmentId}/programs/{programId}")
    public ResponseEntity<Void> removeProgramFromDepartment(
            @PathVariable Long departmentId,
            @PathVariable Long programId) {
        departmentService.removeProgramFromDepartment(departmentId, programId);
        return ResponseEntity.ok().build();
    }


}

