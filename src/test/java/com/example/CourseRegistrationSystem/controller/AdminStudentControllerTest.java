package com.example.CourseRegistrationSystem.controller;

import com.example.CourseRegistrationSystem.entity.Student;
import com.example.CourseRegistrationSystem.entity.Undergrad;
import com.example.CourseRegistrationSystem.service.StudentService;
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

public class AdminStudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private AdminStudentController adminStudentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetStudents() {
        List<Student> students = Arrays.asList(new Undergrad(), new Undergrad());
        when(studentService.getStudents()).thenReturn(students);
        ResponseEntity<List<Student>> response = adminStudentController.getStudents();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetStudent() {
        Student student = new Undergrad();
        when(studentService.getStudent(1L)).thenReturn(student);
        ResponseEntity<Student> response = adminStudentController.getStudent(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(student, response.getBody());
    }

    @Test
    void testCreateStudent() {
        Student student = new Undergrad();
        when(studentService.createStudent(student)).thenReturn(student);
        ResponseEntity<Student> response = adminStudentController.createStudent(student);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(student, response.getBody());
    }

    @Test
    void testUpdateStudent() {
        Student student = new Undergrad();
        when(studentService.updateStudent(1L, student)).thenReturn(student);
        ResponseEntity<Student> response = adminStudentController.updateStudent(1L, student);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(student, response.getBody());
    }

    @Test
    void testDeleteStudent() {
        doNothing().when(studentService).deleteStudent(1L);
        ResponseEntity<Void> response = adminStudentController.deleteStudent(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }
}

