# Login Page

File: `src/pages/Login.jsx`

## Purpose

Handles student authentication.

## UI

- email input
- password input
- submit button with loading state
- error message display

## Behavior

On submit, the page:

- sends `POST http://localhost:8080/api/auth/login`
- includes `email`, `password`, and `role: "STUDENT"` in the request body
- expects a successful response with `success: true`
- extracts a token from the response message if it contains `Token:`
- stores the token in `localStorage`
- decodes the JWT payload to store the role in `localStorage`
- routes to `/student-dashboard`

## Dependencies

- `Input`
- `Button`
- `framer-motion` for the card animation

## Notes

This file does not use the helper functions in `src/api/api.js`; it calls `fetch` directly.
