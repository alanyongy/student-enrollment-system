# Admin Dashboard

File: `src/pages/AdminDashboard.jsx`

## Purpose

Serves as the admin landing page and entry point to the admin management sections.

## UI

- top bar with title and logout button
- grid of clickable admin cards

## Behavior

- logout clears the stored token
- clicking a card navigates to the matching admin entity route
- cards are animated with `framer-motion`

## Data source

Uses `adminCards` from `src/config/adminCards.js`.
