package com.example.CourseRegistrationSystem.controller;

import com.example.CourseRegistrationSystem.entity.Course;
import com.example.CourseRegistrationSystem.entity.Department;
import com.example.CourseRegistrationSystem.entity.Program;
import com.example.CourseRegistrationSystem.service.DepartmentService;
import com.example.CourseRegistrationSystem.service.ProgramService;
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


public class AdminProgramControllerTest {
        
    @Mock
    private ProgramService programService;

    @InjectMocks
    private AdminProgramController adminProgramController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPrograms() {
        List<Program> programs = Arrays.asList(new Program(), new Program());
        when(programService.getAllPrograms()).thenReturn(programs);
        ResponseEntity<List<Program>> response = adminProgramController.getAllPrograms();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetProgramById() {
        Program program = new Program();
        when(programService.getProgramById(1L)).thenReturn(program);
        ResponseEntity<Program> response = adminProgramController.getProgramById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(program, response.getBody());
    }

    @Test
    void testCreateProgram() {
        Program program = new Program();
        when(programService.createProgram(program)).thenReturn(program);
        ResponseEntity<Program> response = adminProgramController.createProgram(program);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(program, response.getBody());
    }

    @Test
    void testUpdateProgram() {
        Program program = new Program();
        when(programService.updateProgram(1L, program)).thenReturn(program);
        ResponseEntity<Program> response = adminProgramController.updateProgram(1L, program);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(program, response.getBody());
    }

    @Test
    void testDeleteProgram() {
        doNothing().when(programService).deleteProgram(1L);
        ResponseEntity<Void> response = adminProgramController.deleteProgram(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(programService).deleteProgram(1L);
    }

}
