# Admin Entities

File: `src/config/adminEntities.js`

## Purpose

Defines the schema used by the generic admin CRUD page.

## What each entity config contains

- `title`: display name
- `endpoint`: backend resource path
- `idKey`: primary key field used for edit and delete actions
- `columns`: table and form fields used by the admin page

## Supported column types

- plain fields, rendered with `Input`
- relation fields, rendered with a `<select>` backed by another admin entity endpoint

## Notes

This file drives the admin CRUD experience for students, instructors, departments, programs, courses, sections, semesters, enrollments, admissions, prerequisites, program-course access, and course completions.

## Important detail

Several relation fields point to nested objects such as `department.deptName` or `course.courseNumber`, which is why the admin entity page includes nested lookup logic.
