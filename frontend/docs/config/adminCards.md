# Admin Cards

File: `src/config/adminCards.js`

## Purpose

Defines the tiles shown on the admin dashboard.

## Card list

- Students -> `/admin/students`
- Instructors -> `/admin/instructors`
- Departments -> `/admin/departments`
- Programs -> `/admin/programs`
- Courses -> `/admin/courses`
- Sections -> `/admin/sections`
- Semesters -> `/admin/semesters`
- Enrollments -> `/admin/enrollments`
- Admissions -> `/admin/admissions`
- Course Prerequisites -> `/admin/course-prerequisites`
- Program Course Access -> `/admin/program-course-access`
- Course Completions -> `/admin/course-completions`

## Notes

These paths are consumed directly by `AdminDashboard` and must match the keys in `adminEntities` for the generic admin page to work.
