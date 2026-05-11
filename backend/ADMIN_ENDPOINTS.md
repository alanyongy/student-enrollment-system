# Admin Endpoints

Source controllers:
- `src/main/java/com/example/CourseRegistrationSystem/controller/AdminStudentController.java`
- `src/main/java/com/example/CourseRegistrationSystem/controller/AdminAdmissionController.java`
- `src/main/java/com/example/CourseRegistrationSystem/controller/AdminCourseController.java`
- `src/main/java/com/example/CourseRegistrationSystem/controller/AdminDepartmentController.java`
- `src/main/java/com/example/CourseRegistrationSystem/controller/AdminInstructorController.java`
- `src/main/java/com/example/CourseRegistrationSystem/controller/AdminProgramController.java`
- `src/main/java/com/example/CourseRegistrationSystem/controller/AdminSectionController.java`
- `src/main/java/com/example/CourseRegistrationSystem/controller/AdminSemesterController.java`

## Auth and common behavior

- `SecurityConfig` protects all `/api/admin/**` routes with `ROLE_ADMIN`.
- For list endpoints, common pagination query params are used: `page`, `size`, `sortBy`, `direction`.
- Response models are mostly entities (`Student`, `Course`, `Section`, etc.) unless noted.

## Student/Admin enrollment/admission/completion operations

| Method | Endpoint | Request | Success Response | Notes |
|---|---|---|---|---|
| GET | `/api/admin/students` | Query: `page,size,sortBy,direction` | `200 OK` `List<Student>` | List students. |
| GET | `/api/admin/students/{id}` | Path: `id` | `200 OK` `Student` | Get one student. |
| POST | `/api/admin/students` | Body: `Student` | `201 Created` `Student` | Create student. |
| PUT | `/api/admin/students/{id}` | Path: `id`, Body: `Student` | `200 OK` `Student` | Update student. |
| DELETE | `/api/admin/students/{id}` | Path: `id` | `204 No Content` | Delete student. |
| DELETE | `/api/admin/students/{studentId}/remove-program/{programId}` | Path: `studentId,programId` | `204 No Content` | Remove student from program. |
| POST | `/api/admin/admissions` | Body: `Admission` (student.personId + program.programId required) | `201 Created` `Admission` | Admission route in `AdminStudentController`. |
| PUT | `/api/admin/admissions/{admissionId}` | Path: `admissionId`, Body: `Admission` | `201 Created` `Admission` | Implementation removes old admission and recreates. |
| DELETE | `/api/admin/admissions/{admissionId}` | Path: `admissionId` | `204 No Content` | Delete admission. |
| GET | `/api/admin/admissions` | Query: `page,size,sortBy,direction` | `200 OK` `List<Admission>` | List admissions. |
| POST | `/api/admin/enrollments` | Method signature expects path vars `studentId,sectionId` | `200 OK` | As implemented, mapping has no `/{studentId}/{sectionId}` in URL. |
| PUT | `/api/admin/enrollments/{enrollmentId}` | Path: `enrollmentId`, Body: `Enrollment` | `200 OK` `Enrollment` | Update enrollment. |
| DELETE | `/api/admin/enrollments/{enrollmentId}` | Path: `enrollmentId` | `204 No Content` | Drop enrollment by ID. |
| GET | `/api/admin/enrollments` | Query: `page,size,sortBy,direction` | `200 OK` `List<Enrollment>` | List enrollments. |
| POST | `/api/admin/students/{studentId}/completed/{courseId}` | Path: `studentId,courseId` | `200 OK` | Mark course as completed. |
| DELETE | `/api/admin/students/{studentId}/completed/{courseId}` | Path: `studentId,courseId` | `204 No Content` | Remove completion mark. |
| GET | `/api/admin/completions` | Query: `page,size,sortBy,direction` | `200 OK` `List<CompletedCourse>` | List completion records. |
| POST | `/api/admin/completions` | Body: `CompletedCourse` | `201 Created` `CompletedCourse` | Create completion record. |
| PUT | `/api/admin/completions/{id}` | Path: `id`, Body: `CompletedCourse` | `200 OK` `CompletedCourse` | Update completion record. |
| DELETE | `/api/admin/completions/{id}` | Path: `id` | `204 No Content` | Delete completion record. |

## Admission controller (separate singular route)

| Method | Endpoint | Request | Success Response | Notes |
|---|---|---|---|---|
| POST | `/api/admin/admission` | Body: `Admission` (student.personId + program.programId required) | `201 Created` `Admission` | Separate from `/api/admin/admissions`. |
| DELETE | `/api/admin/admission/{admissionId}` | Path: `admissionId` | `204 No Content` | Remove admission. |
| GET | `/api/admin/admission` | Query: `page,size,sortBy,direction` | `200 OK` `List<Admission>` | List admissions via singular base route. |

## Course and prerequisite/access operations

| Method | Endpoint | Request | Success Response | Notes |
|---|---|---|---|---|
| GET | `/api/admin/courses` | Query: `page,size,sortBy,direction` | `200 OK` `List<Course>` | List courses. |
| GET | `/api/admin/courses/{id}` | Path: `id` | `200 OK` `Course` | Get one course. |
| POST | `/api/admin/courses` | Body: `Course` | `201 Created` `Course` | Create course. |
| PUT | `/api/admin/courses/{id}` | Path: `id`, Body: `Course` | `200 OK` `Course` | Update course. |
| DELETE | `/api/admin/courses/{id}` | Path: `id` | `204 No Content` | Delete course. |
| GET | `/api/admin/courses/course-prerequisites` | Query: `page,size,sortBy,direction` | `200 OK` `List<CoursePrerequisite>` | List prerequisite mappings. |
| POST | `/api/admin/courses/course-prerequisites` | Body: `CoursePrerequisite` | `201 Created` body returned | Create prerequisite mapping. |
| PUT | `/api/admin/courses/course-prerequisites/{prereqId}` | Path: `prereqId`, Body: `CoursePrerequisite` | `200 OK` body returned | Update prerequisite mapping. |
| DELETE | `/api/admin/courses/course-prerequisites/{prereqId}` | Path: `prereqId` | `204 No Content` | Delete prerequisite mapping. |
| POST | `/api/admin/courses/{courseId}/assign-department/{deptId}` | Path: `courseId,deptId` | `200 OK` | Assign department to course. |
| GET | `/api/admin/courses/access` | Query: `page,size,sortBy,direction` | `200 OK` `List<ProgramCourseAccess>` | List program-course access records. |
| POST | `/api/admin/courses/access` | Body: `ProgramCourseAccess` | `201 Created` (empty body) | Uses `course.courseId` and `program.programId` from body. |
| PUT | `/api/admin/courses/access/{id}` | Path: `id`, Body: `ProgramCourseAccess` | `200 OK` `ProgramCourseAccess` | Update access mapping. |
| DELETE | `/api/admin/courses/access/{accessId}` | Path: `accessId` | `204 No Content` | Delete access mapping. |

## Department operations

| Method | Endpoint | Request | Success Response | Notes |
|---|---|---|---|---|
| GET | `/api/admin/departments` | Query: `page,size,sortBy,direction` | `200 OK` `List<Department>` | List departments. |
| GET | `/api/admin/departments/{id}` | Path: `id` | `200 OK` `Department` | Get one department. |
| POST | `/api/admin/departments` | Body: `Department` | `201 Created` `Department` | Create department. |
| PUT | `/api/admin/departments/{id}` | Path: `id`, Body: `Department` | `200 OK` `Department` | Update department. |
| DELETE | `/api/admin/departments/{id}` | Path: `id` | `204 No Content` | Delete department. |
| POST | `/api/admin/departments/{departmentId}/programs/{programId}` | Path: `departmentId,programId` | `200 OK` | Assign program to department. |
| DELETE | `/api/admin/departments/{departmentId}/programs/{programId}` | Path: `departmentId,programId` | `200 OK` | Remove program from department. |

## Instructor operations

| Method | Endpoint | Request | Success Response | Notes |
|---|---|---|---|---|
| GET | `/api/admin/instructors` | Query: `page,size,sortBy,direction` | `200 OK` `List<Instructor>` | List instructors. |
| GET | `/api/admin/instructors/{id}` | Path: `id` | `200 OK` `Instructor` | Get one instructor. |
| POST | `/api/admin/instructors` | Body: `Instructor` | `201 Created` `Instructor` | Implementation returns request object body. |
| PUT | `/api/admin/instructors/{id}` | Path: `id`, Body: `Instructor` | `200 OK` `Instructor` | Update instructor. |
| DELETE | `/api/admin/instructors/{id}` | Path: `id` | `204 No Content` | Delete instructor. |

## Program operations

| Method | Endpoint | Request | Success Response | Notes |
|---|---|---|---|---|
| GET | `/api/admin/programs` | Query: `page,size,sortBy,direction` | `200 OK` `List<Program>` | List programs. |
| GET | `/api/admin/programs/{id}` | Path: `id` | `200 OK` `Program` | Get one program. |
| POST | `/api/admin/programs` | Body: `Program` | `200 OK` `Program` | As implemented, create returns `200`, not `201`. |
| PUT | `/api/admin/programs/{id}` | Path: `id`, Body: `Program` | `200 OK` `Program` | Update program. |
| DELETE | `/api/admin/programs/{id}` | Path: `id` | `204 No Content` | Delete program. |

## Section operations

| Method | Endpoint | Request | Success Response | Notes |
|---|---|---|---|---|
| GET | `/api/admin/sections` | Query: `page,size,sortBy,direction` | `200 OK` `List<Section>` | List sections. |
| GET | `/api/admin/sections/{id}` | Path: `id` | `200 OK` `Section` | Get one section. |
| POST | `/api/admin/sections` | Body: `Section` | `200 OK` `Section` | As implemented, create returns `200`, not `201`. |
| PUT | `/api/admin/sections/{id}` | Path: `id`, Body: `Section` | `200 OK` `Section` | Update section. |
| DELETE | `/api/admin/sections/{id}` | Path: `id` | `204 No Content` | Delete section. |
| POST | `/api/admin/sections/{sectionId}/assign-instructor/{instructorId}` | Path: `sectionId,instructorId` | `200 OK` | Assign instructor to section. |
| DELETE | `/api/admin/sections/{sectionId}/remove-instructor` | Path: `sectionId` | `200 OK` | Remove instructor from section. |
| POST | `/api/admin/sections/{sectionId}/courses/{courseId}` | Path: `sectionId,courseId` | `200 OK` | Assign course to section. |
| DELETE | `/api/admin/sections/{sectionId}/courses/{courseId}` | Path: `sectionId,courseId` | `200 OK` | Remove course from section. |
| GET | `/api/admin/sections/{sectionId}/courses` | Path: `sectionId` | `200 OK` `List<Long>` | Returns course IDs linked to section. |

## Semester operations

| Method | Endpoint | Request | Success Response | Notes |
|---|---|---|---|---|
| GET | `/api/admin/semesters` | Query: `page,size,sortBy,direction` | `200 OK` `List<Semester>` | List semesters. |
| POST | `/api/admin/semesters` | Body: `Semester` | `200 OK` `Semester` | As implemented, create returns `200`, not `201`. |
| PUT | `/api/admin/semesters/{id}` | Path: `id`, Body: `Semester` | `200 OK` `Semester` | Update semester. |
| DELETE | `/api/admin/semesters/{id}` | Path: `id` | `204 No Content` | Delete semester. |
| POST | `/api/admin/semesters/{semesterId}/sections/{sectionId}` | Path: `semesterId,sectionId` | `200 OK` | Add section to semester. |
| DELETE | `/api/admin/semesters/{semesterId}/sections/{sectionId}` | Path: `semesterId,sectionId` | `204 No Content` | Remove section from semester. |
| GET | `/api/admin/semesters/{semesterId}/sections` | Path: `semesterId` | `200 OK` `List<Long>` | Returns section IDs in semester. |

## Minimal payload references (for submission)

- `Admission` create/update uses nested IDs: `student.personId`, `program.programId`.
- `ProgramCourseAccess` create uses nested IDs: `program.programId`, `course.courseId`.
- `CoursePrerequisite` typically includes `course` and prerequisite course references.
- Standard error statuses in secured routes: `401 Unauthorized`, `403 Forbidden`.

