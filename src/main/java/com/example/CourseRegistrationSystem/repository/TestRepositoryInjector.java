package com.example.CourseRegistrationSystem.repository;

import com.example.CourseRegistrationSystem.dao.CourseDAO;
import com.example.CourseRegistrationSystem.dao.DepartmentDAO;
import com.example.CourseRegistrationSystem.dao.SemesterDAO;
import com.example.CourseRegistrationSystem.entity.*;
import com.example.CourseRegistrationSystem.enums.SecurityRole;
import com.example.CourseRegistrationSystem.service.AuthService;

import jakarta.transaction.Transactional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
public class TestRepositoryInjector {

    @Bean
    @Transactional
    CommandLineRunner init(
            AuthService authService,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            SemesterDAO semesterDAO,
            CourseDAO courseDAO,
            DepartmentDAO departmentDAO
    ) {
        return args -> {

            // --- STUDENT ---
            if (!userRepository.existsByEmail("student@example.com")) {
                Undergrad student = new Undergrad();
                student.setFirstName("Test");
                student.setLastName("Student");
                student.setEmail("student@example.com");
                student.setPassword(passwordEncoder.encode("student123"));
                student.setCreditsEarned(1);
                student.setAcademicStatus("test");
                student.setYearOfStudy(1);
                student.setEnrollmentYear(2026);
                student.setRole(SecurityRole.STUDENT);

                userRepository.save(student);
            }

            // --- ADMIN ---
            if (!userRepository.existsByEmail("admin@example.com")) {
                Person admin = new Administrator();
                admin.setFirstName("Test");
                admin.setLastName("Admin");
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(SecurityRole.ADMIN);

                userRepository.save(admin);
            }

            // --- INSTRUCTOR ---
            Instructor instructor;
            if (!userRepository.existsByEmail("instructor@example.com")) {
                instructor = new Instructor();
                instructor.setFirstName("Test");
                instructor.setLastName("Instructor");
                instructor.setEmail("instructor@example.com");
                instructor.setPassword(passwordEncoder.encode("instructor123"));
                instructor.setRole(SecurityRole.STUDENT);
                instructor.setYearsOfExperience(1);
                instructor.setExpertiseTopics("none");
                userRepository.save(instructor);
            } else {
                instructor = (Instructor) userRepository.findByEmail("instructor@example.com").get();
            }

            // --- SEMESTERS ---
            if (semesterDAO.findAll(0, 10, "semesterId","asc").isEmpty()) {
                Semester fall2026 = new Semester();
                fall2026.setTermName("Fall 2026");
                fall2026.setStartDate(LocalDate.of(2026, 9, 1));
                fall2026.setEndDate(LocalDate.of(2026, 12, 15));
                semesterDAO.save(fall2026);

                Semester spring2027 = new Semester();
                spring2027.setTermName("Spring 2027");
                spring2027.setStartDate(LocalDate.of(2027, 1, 10));
                spring2027.setEndDate(LocalDate.of(2027, 5, 5));
                semesterDAO.save(spring2027);
            }

            // --- COURSES & SECTIONS ---
            if (courseDAO.findAll(0, 10, "courseId","asc").isEmpty()) {
                Semester fall2026 = semesterDAO.findAll(0, 10, "semesterId","asc" ).get(0);

                Department cs = new Department();
                cs.setOfficeLocation("cs office location");
                cs.setDeptName("Computer Science");
                cs.setPhoneNumber("4034034033");
                cs.setDeptEmail("csDept@email.com");
                departmentDAO.save(cs);

                Department math = new Department();
                math.setOfficeLocation("math office location");
                math.setDeptName("Math");
                math.setPhoneNumber("4034034034");
                math.setDeptEmail("mathDept@email.com");
                departmentDAO.save(math);
                

                // Course 1
                Course cs101 = new Course();
                cs101.setCourseNumber("CS101");
                cs101.setDescription("Intro to CS");
                cs101.setCredits(3);
                cs101.setDepartment(cs);

                Section cs101Sec1 = new Section();
                cs101Sec1.setScheduleTime("MWF 9:00-10:00");
                cs101Sec1.setLocation("Room 101");
                cs101Sec1.setCapacity(30);
                cs101Sec1.setInstructor(instructor);
                cs101Sec1.setCourse(cs101);
                cs101Sec1.setSemester(fall2026);

                cs101.addSection(cs101Sec1);
                courseDAO.save(cs101);

                // Course 2
                Course math101 = new Course();
                math101.setCourseNumber("MATH101");
                math101.setDescription("Calculus I");
                math101.setCredits(3);
                math101.setDepartment(math);

                Section math101Sec1 = new Section();
                math101Sec1.setScheduleTime("TTh 11:00-12:30");
                math101Sec1.setLocation("Room 202");
                math101Sec1.setCapacity(25);
                math101Sec1.setInstructor(instructor);
                math101Sec1.setCourse(math101);
                math101Sec1.setSemester(fall2026);

                math101.addSection(math101Sec1);
                courseDAO.save(math101);
            }
        };
    }
}