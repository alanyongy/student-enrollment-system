# Admin Login Page

File: `src/pages/AdminLogin.jsx`

## Purpose

Handles administrator authentication before entering the admin dashboard.

## UI

- email input
- password input
- submit button with loading state
- error message display

## Behavior

On submit, the page:

- sends `POST http://localhost:8080/api/auth/login`
- includes `email`, `password`, and `role: "ADMIN"` in the request body
- expects a successful response with `success: true`
- extracts a token from the response message if it contains `Token:`
- stores the token in `localStorage`
- decodes the JWT payload to store the role in `localStorage`
- routes to `/admin-dashboard`

## Dependencies

- `Input`
- `Button`
- `framer-motion` for the card animation

## Notes

Admin users should open `/admin-login` before going to the admin dashboard.
