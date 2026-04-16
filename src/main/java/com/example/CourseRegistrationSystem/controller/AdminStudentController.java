package com.example.CourseRegistrationSystem.controller;

import com.example.CourseRegistrationSystem.entity.Student;
import com.example.CourseRegistrationSystem.entity.Program;
import com.example.CourseRegistrationSystem.dao.ProgramDAO;
import com.example.CourseRegistrationSystem.entity.Admission;
import com.example.CourseRegistrationSystem.entity.Enrollment;
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
@RequestMapping("/api/admin")
public class AdminStudentController {

    private final StudentService studentService;
    private final AdmissionService admissionService;
    private final ProgramService programService;
    private final EnrollmentService enrollmentservice;
    private final CompletedCourseService completedCourseService;
    private final ProgramDAO programDAO;

    public AdminStudentController(StudentService studentService, AdmissionService admissionService, ProgramService programService, EnrollmentService enrollmentservice, CompletedCourseService completedCourseService, ProgramDAO programDAO) {
        this.studentService = studentService;
        this.admissionService = admissionService;
        this.programService = programService;
        this.enrollmentservice = enrollmentservice;
        this.completedCourseService = completedCourseService;
        this.programDAO = programDAO;
    }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getStudents(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "personId") String sortBy,
        @RequestParam(defaultValue = "asc") String direction
    ) {

        return ResponseEntity.ok(studentService.getStudents(page, size, sortBy, direction));
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {

        return ResponseEntity.ok(studentService.getStudent(id));
    }

    
    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {

        Student savedStudent = studentService.createStudent(student);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @RequestBody Student student) {

        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {

        studentService.deleteStudent(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/students/{studentId}/remove-program/{programId}")
    public ResponseEntity<Void> removeStudentFromProgram(@PathVariable Long studentId, @PathVariable Long programId) {
        Student student = studentService.getStudent(studentId);
        Program program = programDAO.findById(programId);
        admissionService.removeStudentFromProgram(student, program);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/admissions")
    public ResponseEntity<Admission> admitStudentToProgram(@RequestBody Admission admission) {
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
        Program program = programDAO.findById(programId);

        Admission saved = admissionService.admitStudentToProgram(student, program);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    @PutMapping("/admissions/{admissionId}")
    public ResponseEntity<Admission> updateAdmission(@PathVariable Long admissionId, @RequestBody Admission admission) {
        removeStudentFromProgram(admissionId);
        return admitStudentToProgram(admission);
    }

    @DeleteMapping("admissions/{admissionId}")
    public ResponseEntity<Void> removeStudentFromProgram(
            @PathVariable Long admissionId
    ) {
    
        Admission admission = admissionService.getById(admissionId);
    
        Student student = admission.getStudent();
        Program program = admission.getProgram();
    
        admissionService.removeStudentFromProgram(student, program);
    
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/admissions")
    public ResponseEntity<List<Admission>> getAllAdmissions() {
        return ResponseEntity.ok(admissionService.getAllAdmissions());
    }

    @PostMapping("/enrollments")
    public ResponseEntity<Void> enrollStudentInSection(@PathVariable Long studentId, @PathVariable Long sectionId) {
        enrollmentservice.enrollStudentInCourse(studentId, sectionId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/enrollments/{sectionId}")
    public ResponseEntity<Void> removeStudentFromSection(@PathVariable Long studentId, @PathVariable Long sectionId) {
        enrollmentservice.dropStudentFromCourse(studentId, sectionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/enrollments")
    public ResponseEntity<List<Enrollment>> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentservice.getAllEnrollments());
    }

    @PostMapping("/students/{studentId}/completed/{courseId}")
    public ResponseEntity<Void> markCourseCompleted(@PathVariable Long studentId, @PathVariable Long courseId) {
        completedCourseService.markCourseCompleted(studentId, courseId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/students/{studentId}/completed/{courseId}")
    public ResponseEntity<Void> removeCompletedCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        completedCourseService.removeCompletedCourse(studentId, courseId);
        return ResponseEntity.noContent().build();
    }
}
