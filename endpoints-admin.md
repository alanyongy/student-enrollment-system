# Course Registration System Admin Endpoints

## 1. AUTH (For client and user login)
- POST /api/auth/login
- POST /api/auth/logout (optional)

## 2. Admin Dashboard
- GET /api/admin/dashboard

## 3. Student Management (Admin Side)
- GET /api/admin/students
- GET /api/admin/students?sort=lastName,asc
- GET /api/admin/students?department=CS
- GET /api/admin/students/{id}
- POST /api/admin/students
- PUT /api/admin/students/{id}
- DELETE /api/admin/students/{id}

## 4. Instructor Management
- GET /api/admin/instructors
- GET /api/admin/instructors?sort=lastName,asc
- GET /api/admin/instructors/{id}
- POST /api/admin/instructors
- PUT /api/admin/instructors/{id}
- DELETE /api/admin/instructors/{id}

## 5. Department Management
- GET /api/admin/departments
- GET /api/admin/departments/{id}
- POST /api/admin/departments
- PUT /api/admin/departments/{id}
- DELETE /api/admin/departments/{id}

## 6. Program Management
- GET /api/admin/programs
- GET /api/admin/programs/{id}
- POST /api/admin/programs
- PUT /api/admin/programs/{id}
- DELETE /api/admin/programs/{id}

## 7. Course Management
- GET /api/admin/courses
- GET /api/admin/courses/{id}
- POST /api/admin/courses
- PUT /api/admin/courses/{id}
- DELETE /api/admin/courses/{id}

## 8. Semester Management
- GET /api/admin/semesters
- POST /api/admin/semesters
- PUT /api/admin/semesters/{id}
- DELETE /api/admin/semesters/{id}

## 9. Section Management
- GET /api/admin/sections
- GET /api/admin/sections/{id}
- POST /api/admin/sections
- PUT /api/admin/sections/{id}
- DELETE /api/admin/sections/{id}

## 10. Relationship Endpoints

### Assign Instructor to Section (TEACHES)
- POST   /api/admin/sections/{sectionId}/assign-instructor/{instructorId}
- DELETE /api/admin/sections/{sectionId}/remove-instructor

### Add Course Prerequisite (PRE_REQ_OF)
- POST   /api/admin/courses/{courseId}/prerequisites/{prereqId}
- DELETE /api/admin/courses/{courseId}/prerequisites/{prereqId}

### Add Course to Department (OFFERS)
- POST /api/admin/departments/{deptId}/courses/{courseId}

### Add Program to Department
- POST /api/admin/departments/{deptId}/programs/{programId}

### Admit Student to Program (ADMITTED)
- POST /api/admin/students/{studentId}/admit/{programId}

### Set Course Required for Program (GIVES_ACCESS / is_required)
- POST   /api/admin/programs/{programId}/required-courses/{courseId}
- DELETE /api/admin/programs/{programId}/required-courses/{courseId}

### Mark Course Completed (COMPLETED)
- POST /api/admin/students/{studentId}/completed/{courseId}

### Enroll Student in Section (ENROLLS)
- POST   /api/admin/students/{studentId}/enroll/{sectionId}
- DELETE /api/admin/students/{studentId}/drop/{sectionId}

