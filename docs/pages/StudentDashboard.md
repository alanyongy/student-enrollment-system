# Student Dashboard

File: `src/pages/StudentDashboard.jsx`

## Purpose

Main student workspace for viewing the schedule, browsing sections, and managing enrollments.

## Data it fetches

- student profile from `/api/me/profile`
- semesters from `/api/student/semesters`
- current enrollments from `/api/student/enrollments`
- section details for the active semester from `/api/student/semesters/:id/sections/details`

## Main features

- semester tabs
- schedule summary and total credits
- current schedule list
- available section list
- add-section confirmation modal
- drop section action for open semesters
- notices for validation and API results

## Important logic

- checks time conflicts before allowing enrollment
- checks section capacity
- blocks add/drop actions when the semester is closed
- enriches enrollment rows with section details when needed

## Dependencies

- `api` from `src/api/api.js`
- `Navbar`

## Notes

The page uses a number of local helper functions to normalize semester labels, detect closed semesters, and compare schedule times.
