package com.example.CourseRegistrationSystem.controller;

import com.example.CourseRegistrationSystem.entity.Student;
import com.example.CourseRegistrationSystem.entity.Program;
import com.example.CourseRegistrationSystem.entity.Admission;
import com.example.CourseRegistrationSystem.service.StudentService;
import com.example.CourseRegistrationSystem.service.AdmissionService;
import com.example.CourseRegistrationSystem.service.ProgramService;
import com.example.CourseRegistrationSystem.service.EnrollmentService;
import com.example.CourseRegistrationSystem.service.CompletedCourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/students")
public class AdminStudentController {

    private final StudentService studentService;
    private final AdmissionService admissionService;
    private final ProgramService programService;
    private final EnrollmentService enrollmentservice;
    private final CompletedCourseService completedCourseService;

    public AdminStudentController(StudentService studentService, AdmissionService admissionService, ProgramService programService, EnrollmentService enrollmentservice, CompletedCourseService completedCourseService) {
        this.studentService = studentService;
        this.admissionService = admissionService;
        this.programService = programService;
        this.enrollmentservice = enrollmentservice;
        this.completedCourseService = completedCourseService;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getStudents(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "personId") String sortBy,
        @RequestParam(defaultValue = "asc") String direction
    ) {

        return ResponseEntity.ok(studentService.getStudents(page, size, sortBy, direction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {

        return ResponseEntity.ok(studentService.getStudent(id));
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {

        Student savedStudent = studentService.createStudent(student);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @RequestBody Student student) {

        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {

        studentService.deleteStudent(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{studentId}/admit/{programId}")
    public ResponseEntity<Admission> admitStudentToProgram(@PathVariable Long studentId, @PathVariable Long programId) {
        Student student = studentService.getStudent(studentId);
        Program program = programService.getProgramById(programId);
        Admission admission = admissionService.admitStudentToProgram(student, program);
        return ResponseEntity.status(HttpStatus.CREATED).body(admission);
    }

    @DeleteMapping("/{studentId}/remove-program/{programId}")
    public ResponseEntity<Void> removeStudentFromProgram(@PathVariable Long studentId, @PathVariable Long programId) {
        Student student = studentService.getStudent(studentId);
        Program program = programService.getProgramById(programId);
        admissionService.removeStudentFromProgram(student, program);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{studentId}/enroll/{sectionId}")
    public ResponseEntity<Void> enrollStudentInSection(@PathVariable Long studentId, @PathVariable Long sectionId) {
        enrollmentservice.enrollStudentInCourse(studentId, sectionId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{studentId}/remove-section/{sectionId}")
    public ResponseEntity<Void> removeStudentFromSection(@PathVariable Long studentId, @PathVariable Long sectionId) {
        enrollmentservice.dropStudentFromCourse(studentId, sectionId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{studentId}/completed/{courseId}")
    public ResponseEntity<Void> markCourseCompleted(@PathVariable Long studentId, @PathVariable Long courseId) {
        completedCourseService.markCourseCompleted(studentId, courseId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{studentId}/completed/{courseId}")
    public ResponseEntity<Void> removeCompletedCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        completedCourseService.removeCompletedCourse(studentId, courseId);
        return ResponseEntity.noContent().build();
    }
}
