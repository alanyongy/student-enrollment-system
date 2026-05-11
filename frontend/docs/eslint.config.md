# ESLint Configuration

File: `eslint.config.js`

## Purpose

Defines linting rules for the React app.

## What it does

- ignores the `dist` directory
- applies to `**/*.{js,jsx}` files
- uses the recommended JavaScript, React Hooks, and React Refresh configs
- configures browser globals and modern ECMAScript parsing
- enforces `no-unused-vars` with an exception for capitalized names and underscore-prefixed placeholders

## Notes

This keeps the linting setup aligned with a modern Vite React project.
