# App Routes

File: `src/routes/AppRoutes.jsx`

## Purpose

Defines the client-side routes for the whole application.

## Routes

- `/` -> `Login`
- `/login` -> `Login`
- `/admin-login` -> `Login`
- `/admin-dashboard` -> `AdminDashboard`
- `/student-dashboard` -> `StudentDashboard`
- `/admin/:entity` -> `AdminEntityPage`
- `/profile` -> `StudentProfile`
- `*` -> `Login`

## Notes

The wildcard route falls back to the login screen.
