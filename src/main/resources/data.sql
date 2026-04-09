-- ========================
-- PERSONS (BASE TABLE)
-- ========================

INSERT INTO persons (person_id, first_name, last_name, email, password, security_role)
VALUES (1, 'Test', 'Student', 'student@example.com',
        '$2a$10$M67C/BLkfotfl14WbFSh8Out/6ReJxaLunI.WsdTPNS66.VLmJ9KG', 'STUDENT');

INSERT INTO persons (person_id, first_name, last_name, email, password, security_role)
VALUES (2, 'Test', 'Admin', 'admin@example.com',
        '$2a$10$0x8ApgZHZ6MgcSA94nfP7.m/az.ZXfSeBT07nPAOPrcbuVBIf3qrG', 'ADMIN');

INSERT INTO persons (person_id, first_name, last_name, email, password, security_role)
VALUES (3, 'Test', 'Instructor', 'instructor@example.com',
        '$2a$10$M67C/BLkfotfl14WbFSh8Out/6ReJxaLunI.WsdTPNS66.VLmJ9KG', 'STUDENT');


-- ========================
-- SUBCLASS TABLES (JOINED)
-- ========================

-- ADMINISTRATOR
INSERT INTO administrator (person_id)
VALUES (2);

-- INSTRUCTOR
INSERT INTO instructors (person_id, years_of_experience, expertise_topics)
VALUES (3, 1, 'none');

-- STUDENT
INSERT INTO students (person_id, credits_earned, academic_status, enrollment_year)
VALUES (1, 1, 'test', 2026);

-- UNDERGRAD 
INSERT INTO undergrads (person_id, year_of_study)
VALUES (1, 1);

-- ========================
-- SEMESTERS
-- ========================

INSERT INTO semesters (semester_id, term_name, start_date, end_date)
VALUES (1, 'Fall 2026', '2026-09-01', '2026-12-15');

INSERT INTO semesters (semester_id, term_name, start_date, end_date)
VALUES (2, 'Spring 2027', '2027-01-10', '2027-05-05');


-- ========================
-- DEPARTMENTS
-- ========================

INSERT INTO departments (dept_id, dept_name, dept_email, phone_number, office_location)
VALUES (1, 'Computer Science', 'csDept@email.com', '4034034033', 'cs office location');

INSERT INTO departments (dept_id, dept_name, dept_email, phone_number, office_location)
VALUES (2, 'Math', 'mathDept@email.com', '4034034034', 'math office location');


-- ========================
-- COURSES
-- ========================

INSERT INTO courses (course_id, course_number, description, credits, dept_id)
VALUES (1, 'CS101', 'Intro to CS', 3, 1);

INSERT INTO courses (course_id, course_number, description, credits, dept_id)
VALUES (2, 'MATH101', 'Calculus I', 3, 2);


-- ========================
-- SECTIONS
-- ========================

INSERT INTO sections (section_id, schedule_time, location, capacity,
                      course_id, instructor_id, semester_id)
VALUES (1, 'MWF 9:00-10:00', 'Room 101', 30, 1, 3, 1);

INSERT INTO sections (section_id, schedule_time, location, capacity,
                      course_id, instructor_id, semester_id)
VALUES (2, 'TTh 11:00-12:30', 'Room 202', 25, 2, 3, 1);