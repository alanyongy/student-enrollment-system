# Navbar

File: `src/components/Navbar.jsx`

## Purpose

Shared student navigation bar for the dashboard and profile screens.

## Props

- `studentName`: shown in the welcome heading
- `activePage`: controls which nav state is highlighted, defaults to `dashboard`

## Behavior

- the welcome heading navigates back to the student dashboard
- the Profile button toggles between profile and dashboard
- Logout clears `token` and `role` from `localStorage` and sends the user to `/login`

## Used by

- `src/pages/StudentDashboard.jsx`
- `src/pages/StudentProfile.jsx`
