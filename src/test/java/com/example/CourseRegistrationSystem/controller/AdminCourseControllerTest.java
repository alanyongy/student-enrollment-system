package com.example.CourseRegistrationSystem.controller;

import com.example.CourseRegistrationSystem.entity.Course;
import com.example.CourseRegistrationSystem.service.CourseService;
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

public class AdminCourseControllerTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private AdminCourseController adminCourseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCourses() {
        List<Course> courses = Arrays.asList(new Course(), new Course());
        when(courseService.getCourses()).thenReturn(courses);
        ResponseEntity<List<Course>> response = adminCourseController.getCourses();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetCourse() {
        Course course = new Course();
        when(courseService.getCourse(1L)).thenReturn(course);
        ResponseEntity<Course> response = adminCourseController.getCourse(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(course, response.getBody());
    }

    @Test
    void testCreateCourse() {
        Course course = new Course();
        when(courseService.createCourse(course)).thenReturn(course);
        ResponseEntity<Course> response = adminCourseController.createCourse(course);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(course, response.getBody());
    }

    @Test
    void testUpdateCourse() {
        Course course = new Course();
        when(courseService.updateCourse(1L, course)).thenReturn(course);
        ResponseEntity<Course> response = adminCourseController.updateCourse(1L, course);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(course, response.getBody());
    }

    @Test
    void testDeleteCourse() {
        doNothing().when(courseService).deleteCourse(1L);
        ResponseEntity<Void> response = adminCourseController.deleteCourse(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }
}

