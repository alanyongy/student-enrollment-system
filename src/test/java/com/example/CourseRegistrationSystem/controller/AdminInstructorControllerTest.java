package com.example.CourseRegistrationSystem.controller;

import com.example.CourseRegistrationSystem.entity.Instructor;
import com.example.CourseRegistrationSystem.service.InstructorService;
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

public class AdminInstructorControllerTest {

    @Mock
    private InstructorService instructorService;

    @InjectMocks
    private AdminInstructorController adminInstructorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetInstructors() {
        List<Instructor> instructors = Arrays.asList(new Instructor(), new Instructor());
        when(instructorService.getInstructors()).thenReturn(instructors);
        ResponseEntity<List<Instructor>> response = adminInstructorController.getInstructors();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetInstructor() {
        Instructor instructor = new Instructor();
        when(instructorService.getInstructor(1L)).thenReturn(instructor);
        ResponseEntity<Instructor> response = adminInstructorController.getInstructor(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(instructor, response.getBody());
    }

    @Test
    void testCreateInstructor() {
        Instructor instructor = new Instructor();
        when(instructorService.createInstructor(instructor)).thenReturn(instructor);
        ResponseEntity<Instructor> response = adminInstructorController.createInstructor(instructor);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(instructor, response.getBody());
    }

    @Test
    void testUpdateInstructor() {
        Instructor instructor = new Instructor();
        when(instructorService.updateInstructor(1L, instructor)).thenReturn(instructor);
        ResponseEntity<Instructor> response = adminInstructorController.updateInstructor(1L, instructor);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(instructor, response.getBody());
    }

    @Test
    void testDeleteInstructor() {
        doNothing().when(instructorService).deleteInstructor(1L);
        ResponseEntity<Void> response = adminInstructorController.deleteInstructor(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }
}

