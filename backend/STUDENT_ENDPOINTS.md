# Student Endpoints

Source controllers:
- `src/main/java/com/example/CourseRegistrationSystem/controller/StudentSemesterController.java`
- `src/main/java/com/example/CourseRegistrationSystem/controller/StudentSectionController.java`
- `src/main/java/com/example/CourseRegistrationSystem/controller/StudentCourseController.java`
- `src/main/java/com/example/CourseRegistrationSystem/controller/StudentEnrollmentController.java`
- `src/main/java/com/example/CourseRegistrationSystem/controller/StudentProfileController.java`

## Auth and access behavior

- `SecurityConfig` protects all `/api/student/**` routes with `ROLE_STUDENT`.
- `GET /api/me/profile` is not under `/api/student/**`, but controller logic still requires authenticated user to be a `Student`.

## Semester and section discovery

| Method | Endpoint | Request | Success Response | Notes |
|---|---|---|---|---|
| GET | `/api/student/semesters` | Authenticated student | `200 OK` `List<SemesterDTO>` | Filters semesters by student's `enrollmentYear` logic in controller. |
| GET | `/api/student/semesters/{semesterId}/sections` | Path: `semesterId` | `200 OK` `List<Long>` | Returns section IDs for that semester. |
| GET | `/api/student/semesters/{semesterId}/sections/details` | Path: `semesterId` | `200 OK` `ApiResponse<List<EnrollmentResponseDTO>>` | Newly added details endpoint; returns section details via service mapping. |
| GET | `/api/student/sections/{id}` | Path: `id` | `200 OK` `Section` | Fetch one section by ID. |
| GET | `/api/student/sections/{sectionId}/courses` | Path: `sectionId` | `200 OK` `List<Long>` | Returns course IDs linked to section. |

## Course browsing and eligibility

| Method | Endpoint | Request | Success Response | Notes |
|---|---|---|---|---|
| GET | `/api/student/courses` | Query: `semesterId` | `200 OK` `List<CourseDTO>` | Lists courses available in semester. |
| GET | `/api/student/courses/{courseId}` | Path: `courseId`, Query: `semesterId` | `200 OK` `CourseDTO` | Returns course only if it belongs to given semester. |
| GET | `/api/student/courses/{courseId}/eligibility` | Path: `courseId`, Query: `semesterId` | `200 OK` `Boolean` | Checks prerequisites against completed courses; `semesterId` required by API signature. |
| GET | `/api/student/completed-courses` | Authenticated student | `200 OK` `List<CourseDTO>` | Returns completed courses for current student. |

## Enrollment operations

| Method | Endpoint | Request | Success Response | Notes |
|---|---|---|---|---|
| POST | `/api/student/enrollments` | Body: `EnrollmentRequestDTO` | `201 Created` `ApiResponse<EnrollmentResponseDTO>` | Service returns typed error categories mapped to HTTP status. |
| GET | `/api/student/enrollments` | Authenticated student | `200 OK` `ApiResponse<List<EnrollmentResponseDTO>>` | Returns current student's enrollments. |
| DELETE | `/api/student/enrollments/{sectionId}` | Path: `sectionId` | `200 OK` `ApiResponse<Void>` | Returns `404` when not enrolled in the section. |

## Student profile

| Method | Endpoint | Request | Success Response | Notes |
|---|---|---|---|---|
| GET | `/api/me/profile` | Authenticated student | `200 OK` `StudentProfileDTO` | Includes base student identity fields and academic info. |

## DTO details (for submission)

- `EnrollmentRequestDTO`:
  - `sectionId` (Long, required)

- `ApiResponse<T>` wrapper:
  - `success` (Boolean)
  - `message` (String)
  - `data` (T or `null`)
  - `errors` (`List<String>` or `null`)

- `EnrollmentResponseDTO` (used in enrollments and section-details):
  - Enrollment/section/course summary fields returned by `StudentEnrollmentService` and `SectionService` mapping.

- `CourseDTO`:
  - `courseId`, `courseNumber`, `description`, `credits`, `departmentName`

- `SemesterDTO`:
  - `semesterId`, `termName`, `startDate`, `endDate`

- `StudentProfileDTO`:
  - Identity/contact: `id`, `firstName`, `middleName`, `lastName`, `email`, `phoneNumber`, `dateOfBirth`
  - Academic: `enrollmentYear`, `academicStatus`, `creditsEarned`, `gpa`, `yearOfStudy` (for undergrad)

## Enrollment API quick reference

### POST `/api/student/enrollments`

Request body:

```json
{
  "sectionId": 5
}
```

Success response shape:

```json
{
  "success": true,
  "message": "Enrolled successfully",
  "data": {
    "enrollmentId": 101,
    "courseName": "Data Structures",
    "sectionId": 5,
    "semester": "Winter 2026"
  },
  "errors": null
}
```

Failure response shape:

```json
{
  "success": false,
  "message": "Enrollment failed",
  "data": null,
  "errors": [
    "Already enrolled in this course",
    "Section is full",
    "Prerequisites not satisfied",
    "Schedule conflict detected",
    "Maximum course limit reached",
    "Invalid section"
  ]
}
```

