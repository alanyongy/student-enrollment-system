package com.example.CourseRegistrationSystem.controller;

import com.example.CourseRegistrationSystem.entity.Semester;
import com.example.CourseRegistrationSystem.service.SemesterService;
import com.example.CourseRegistrationSystem.service.SemesterSectionService;
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

public class AdminSemesterControllerTest {
    
    @Mock
    private SemesterService semesterService;

    @Mock
    private SemesterSectionService semesterSectionService;

    @InjectMocks
    private AdminSemesterController adminSemesterController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllSemesters() {
        List<Semester> semesters = Arrays.asList(new Semester(), new Semester());
        when(semesterService.getAllSemesters(0, 10, "semesterId", "asc")).thenReturn(semesters);
        ResponseEntity<List<Semester>> response = adminSemesterController.getAllSemesters(0, 10, "semesterId", "asc");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testCreateSemester() {
        Semester semester = new Semester();
        when(semesterService.createSemester(semester)).thenReturn(semester);
        ResponseEntity<Semester> response = adminSemesterController.createSemester(semester);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(semester, response.getBody());
    }

    @Test
    void testUpdateSemester() {
        Semester semester = new Semester();
        when(semesterService.updateSemester(1L, semester)).thenReturn(semester);
        ResponseEntity<Semester> response = adminSemesterController.updateSemester(1L, semester);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(semester, response.getBody());
    }

    @Test
    void testDeleteSemester() {
        doNothing().when(semesterService).deleteSemester(1L);
        ResponseEntity<Void> response = adminSemesterController.deleteSemester(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(semesterService).deleteSemester(1L);
    }

    @Test
    void testAddSectionToSemester() {
        doNothing().when(semesterSectionService).addSemesterSection(1L, 2L);
        ResponseEntity<Void> response = adminSemesterController.addSectionToSemester(1L, 2L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(semesterSectionService).addSemesterSection(1L, 2L);
    }

    @Test
    void testRemoveSectionFromSemester() {
        doNothing().when(semesterSectionService).removeSemesterSection(1L, 2L);
        ResponseEntity<Void> response = adminSemesterController.removeSectionFromSemester(1L, 2L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(semesterSectionService).removeSemesterSection(1L, 2L);
    }

}
