# App

File: `src/App.jsx`

## Purpose

Wraps the application in `BrowserRouter` and renders the route tree.

## What it does

- Imports `AppRoutes`
- Mounts `BrowserRouter` at the top level
- Keeps routing centralized in one place

## Notes

`src/App.jsx` currently imports `BrowserRouter` and also aliases it as `Router`, but only `BrowserRouter` is used.
