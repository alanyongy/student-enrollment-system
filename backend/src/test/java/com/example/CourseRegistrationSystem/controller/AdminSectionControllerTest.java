package com.example.CourseRegistrationSystem.controller;

import com.example.CourseRegistrationSystem.entity.Section;
import com.example.CourseRegistrationSystem.service.SectionService;
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

public class AdminSectionControllerTest {
    
    @Mock
    private SectionService sectionService;

    @InjectMocks
    private AdminSectionController adminSectionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllSections() {
        List<Section> sections = Arrays.asList(new Section(), new Section());
        when(sectionService.getAllSections(0, 10, "sectionId", "asc")).thenReturn(sections);
        ResponseEntity<List<Section>> response = adminSectionController.getAllSections(0, 10, "sectionId", "asc");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetSectionById() {
        Section section = new Section();
        when(sectionService.getSectionById(1L)).thenReturn(section);
        ResponseEntity<Section> response = adminSectionController.getSectionById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(section, response.getBody());
    }

    @Test
    void testCreateSection() {
        Section section = new Section();
        when(sectionService.createSection(section)).thenReturn(section);
        ResponseEntity<Section> response = adminSectionController.createSection(section);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(section, response.getBody());
    }

    @Test
    void testUpdateSection() {
        Section section = new Section();
        when(sectionService.updateSection(1L, section)).thenReturn(section);
        ResponseEntity<Section> response = adminSectionController.updateSection(1L, section);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(section, response.getBody());
    }

    @Test
    void testDeleteSection() {
        doNothing().when(sectionService).deleteSection(1L);
        ResponseEntity<Void> response = adminSectionController.deleteSection(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(sectionService).deleteSection(1L);
    }

    @Test
    void testAssignInstructorToSection() {
        doNothing().when(sectionService).assignInstructor(1L, 2L);
        ResponseEntity<Void> response = adminSectionController.assignInstructorToSection(1L, 2L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(sectionService).assignInstructor(1L, 2L);
    }

    @Test
    void testRemoveInstructorFromSection() {
        doNothing().when(sectionService).removeInstructor(1L);
        ResponseEntity<Void> response = adminSectionController.removeInstructorFromSection(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(sectionService).removeInstructor(1L);
    }    

    @Test
    void testAssignCourseToSection() {
        doNothing().when(sectionService).assignCourse(1L, 2L);
        ResponseEntity<Void> response = adminSectionController.assignCourseToSection(1L, 2L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(sectionService).assignCourse(1L, 2L);
    }

    @Test
    void testRemoveCourseFromSection() {
        doNothing().when(sectionService).removeCourse(1L, 2L);
        ResponseEntity<?> response = adminSectionController.removeCourseToSection(1L, 2L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(sectionService).removeCourse(1L, 2L);
    }    

}
