package com.example.CourseRegistrationSystem.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.CourseRegistrationSystem.entity.Admission;
import com.example.CourseRegistrationSystem.entity.Program;
import com.example.CourseRegistrationSystem.entity.Student;
import com.example.CourseRegistrationSystem.service.AdmissionService;
import com.example.CourseRegistrationSystem.service.ProgramService;
import com.example.CourseRegistrationSystem.service.StudentService;

@RestController
@RequestMapping("/api/admin/admission")
public class AdminAdmissionController {

    private final StudentService studentService;
    private final AdmissionService admissionService;
    private final ProgramService programService;

    public AdminAdmissionController(
            StudentService studentService,
            AdmissionService admissionService,
            ProgramService programService
    ) {
        this.studentService = studentService;
        this.admissionService = admissionService;
        this.programService = programService;
    }

    @PostMapping
    public ResponseEntity<Admission> admitStudentToProgram(
            @RequestBody Admission admission
    ) {

        if (admission.getStudent() == null ||
            admission.getStudent().getPersonId() == null) {
            throw new IllegalArgumentException("personId is required");
        }

        if (admission.getProgram() == null ||
            admission.getProgram().getProgramId() == null) {
            throw new IllegalArgumentException("programId is required");
        }

        Long personId = admission.getStudent().getPersonId();
        Long programId = admission.getProgram().getProgramId();

        Student student = studentService.getStudent(personId);
        Program program = programService.getProgramById(programId);

        Admission saved = admissionService.admitStudentToProgram(student, program);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    @DeleteMapping("/{admissionId}")
    public ResponseEntity<Void> removeStudentFromProgram(
            @PathVariable Long admissionId
    ) {
    
        Admission admission = admissionService.getById(admissionId);
    
        Student student = admission.getStudent();
        Program program = admission.getProgram();
    
        admissionService.removeStudentFromProgram(student, program);
    
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Admission>> getAllAdmissions(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "admissionId") String sortBy,
        @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(admissionService.getAllAdmissions(page, size, sortBy, direction));
    }
}